package com.smartcity.service;

import com.smartcity.dao.TaskDao;
import com.smartcity.domain.Task;
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
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    private TaskDto taskDto = new TaskDto(2L, "Santa", "Task for Santa",
            LocalDateTime.now(), "TODO",
            1000L, 1000L,
            LocalDateTime.now(), LocalDateTime.now(),
            1L);

    @Mock
    private TaskDao taskDao;

    private TaskDtoMapper taskDtoMapper = new TaskDtoMapper();

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        taskService = new TaskServiceImpl(taskDao, taskDtoMapper);
        task = taskDtoMapper.mapDto(taskDto);
    }

    @Test
    void testCreateTask() {
        doReturn(task).when(taskDao).create(task);
        TaskDto result = taskService.create(taskDto);
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
        doReturn(task).when(taskDao).update(task);
        TaskDto result = taskService.update(taskDto);
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
}