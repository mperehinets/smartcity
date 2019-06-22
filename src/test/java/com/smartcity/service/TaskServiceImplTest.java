package com.smartcity.service;

import com.smartcity.dao.BudgetDao;
import com.smartcity.dao.TaskDao;
import com.smartcity.dao.TransactionDao;
import com.smartcity.domain.Budget;
import com.smartcity.domain.Task;
import com.smartcity.domain.Transaction;
import com.smartcity.dto.TaskDto;
import com.smartcity.mapperDto.TaskDtoMapper;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    private TaskDto taskDto = new TaskDto(2L, "Santa", "Task for Santa",
            LocalDateTime.now(), "ToDo",
            1000L, 1000L,
            LocalDateTime.now(), LocalDateTime.now(),
            1L);

    @Mock
    private TaskDao taskDao;

    @Mock
    private TransactionDao transDao;

    @Mock
    private BudgetDao budgetDao;

    private TaskDtoMapper taskDtoMapper = new TaskDtoMapper();

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        taskService = new TaskServiceImpl(taskDao, taskDtoMapper, transDao, budgetDao);
        task = taskDtoMapper.mapDto(taskDto);
    }

    @Test
    void testCreateTask() {
        Budget budget = new Budget(2000L);
        Transaction trans = new Transaction(null, task.getId(),budget.getValue(),
                task.getApprovedBudget(), null, null);
        doReturn(task).when(taskDao).create(task);
        doReturn(budget).when(budgetDao).get();
        doReturn(trans).when(transDao).create(trans);
        doReturn(budget).when(budgetDao).createOrUpdate(budget);
        TaskDto result = taskService.create(taskDto);
        verify(taskDao, times(1)).create(task);
        verify(budgetDao, times(2)).get();
        verify(transDao, times(1)).create(trans);
        assertThat(result).isEqualToIgnoringGivenFields(taskDtoMapper.mapRow(task),
                "transactionList", "deadlineDate",
                "createdAt", "updatedAt");
    }

    @Test
    void testFindTaskById() {
        doReturn(task).when(taskDao).findById(task.getId());
        TaskDto result = taskService.findById(taskDto.getId());
        assertThat(result).isEqualToIgnoringGivenFields(taskDtoMapper.mapRow(task),
                "transactionList", "deadlineDate",
                "createdAt", "updatedAt");
    }

    @Test
    void testFindTasksByOrganizationId() {
        List<Task> taskList = Collections.singletonList(task);
        List<TaskDto> taskDtoList = Collections.singletonList(taskDtoMapper.mapRow(task));
        doReturn(taskList).when(taskDao).findByOrganizationId(task.getUsersOrganizationsId());
        List<TaskDto> result = taskService.findByOrganizationId(taskDto.getUsersOrganizationsId());
        assertEquals(result, taskDtoList);
    }

    @Test
    void testFindTasksByUserId() {
        List<Task> taskList = Collections.singletonList(task);
        List<TaskDto> taskDtoList = Collections.singletonList(taskDtoMapper.mapRow(task));
        doReturn(taskList).when(taskDao).findByUserId(task.getUsersOrganizationsId());
        List<TaskDto> result = taskService.findByUserId(taskDto.getUsersOrganizationsId());
        assertEquals(result, taskDtoList);
    }

    @Test
    void testUpdateTask() {
        Budget budget = new Budget(2000L);
        Transaction trans = new Transaction(null, task.getId(),budget.getValue(),
                0L,
                null, null);
        doReturn(task).when(taskDao).update(task);
        doReturn(budget).when(budgetDao).get();
        doReturn(trans).when(transDao).create(trans);
        doReturn(budget).when(budgetDao).createOrUpdate(budget);
        doReturn(task).when(taskDao).findById(task.getId());
        TaskDto result = taskService.update(taskDto);
        verify(taskDao, times(1)).update(task);
        verify(budgetDao, times(2)).get();
        verify(transDao, times(1)).create(trans);
        verify(taskDao, times(1)).findById(task.getId());
        assertThat(result).isEqualToIgnoringGivenFields(taskDtoMapper.mapRow(task),
                "transactionList", "deadlineDate",
                "createdAt", "updatedAt");
    }

    @Test
    void testDeleteTask() {
        doReturn(true).when(taskDao).delete(task.getId());
        boolean result = taskService.delete(taskDto.getId());
        assertTrue(result);
    }

    @Test
    void findUsersOrgsId(){
        doReturn(1L).when(taskDao).findUsersOrgIdByUserIdAndOrgId(1L, 1L);
        Long result = taskService.findUsersOrgIdByUserIdAndOrgId(1L, 1L);
        assertEquals(1L, result);
    }
}