package com.smartcity.service;

import com.smartcity.dao.BudgetDao;
import com.smartcity.dao.TaskDao;
import com.smartcity.dao.TransactionDao;
import com.smartcity.domain.Budget;
import com.smartcity.domain.Task;
import com.smartcity.domain.Transaction;
import com.smartcity.dto.TaskDto;
import com.smartcity.mapperDto.TaskDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskDao taskDao;
    private TransactionDao transDao;
    private BudgetDao budgetDao;
    private TaskDtoMapper taskDtoMapper;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao, TaskDtoMapper taskDtoMapper, TransactionDao transDao, BudgetDao budgetDao) {
        this.taskDao = taskDao;
        this.taskDtoMapper = taskDtoMapper;
        this.transDao = transDao;
        this.budgetDao = budgetDao;
    }

    @Override
    @Transactional
    public TaskDto create(TaskDto task) {
        Task taskResult = taskDao.create(taskDtoMapper.mapDto(task));
        transDao.create(new Transaction(null, taskResult.getId(), budgetDao.get().getValue(),
                task.getApprovedBudget(), null, null));
        Budget budget = new Budget(budgetDao.get().getValue() - taskResult.getApprovedBudget());
        budgetDao.createOrUpdate(budget);
        return taskDtoMapper.mapRow(taskResult);
    }

    @Override
    public TaskDto findById(Long id) {
        return taskDtoMapper.mapRow(taskDao.findById(id));
    }

    @Override
    public List<TaskDto> findByOrganizationId(Long id) {
        List<Task> tasks = taskDao.findByOrganizationId(id);
        return mapListDto(tasks);
    }

    @Override
    public List<TaskDto> findByUserId(Long id) {
        List<Task> tasks = taskDao.findByUserId(id);
        return mapListDto(tasks);
    }

    @Override
    public List<TaskDto> findByDate(Long id, LocalDateTime from, LocalDateTime to) {
        List<Task> tasks = taskDao.findByDate(id, from, to);
        return mapListDto(tasks);
    }

    @Override
    @Transactional
    public TaskDto update(TaskDto task) {
        Task taskFromDb = taskDao.findById(task.getId());
        Task taskResult = taskDao.update(taskDtoMapper.mapDto(task));
        Long approvedBudget = taskResult.getApprovedBudget() - taskFromDb.getApprovedBudget();
        if(approvedBudget != 0) {
            transDao.create(new Transaction(null, taskResult.getId(), budgetDao.get().getValue(),
                    approvedBudget, null, null));
            Budget budget = new Budget(budgetDao.get().getValue() - approvedBudget);
            budgetDao.createOrUpdate(budget);
        }
        return taskDtoMapper.mapRow(taskResult);
    }

    @Override
    public boolean delete(Long id) {
        return taskDao.delete(id);
    }

    @Override
    public Long findUsersOrgIdByUserIdAndOrgId(Long userId, Long orgId) {
        return taskDao.findUsersOrgIdByUserIdAndOrgId(userId, orgId);
    }

    private List<TaskDto> mapListDto(List<Task> tasks) {
        return tasks.stream().map(taskDtoMapper::mapRow).collect(Collectors.toList());
    }
}
