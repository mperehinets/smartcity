package com.smartcity.service;

import com.smartcity.dao.*;
import com.smartcity.domain.Budget;
import com.smartcity.domain.Task;
import com.smartcity.domain.Transaction;
import com.smartcity.dto.TaskDto;
import com.smartcity.dto.TaskNotificationDto;
import com.smartcity.mapperDto.TaskDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private SimpMessagingTemplate simpMessagingTemplate;
    private UserDao userDao;
    private UserOrganizationDao userOrganizationDao;
    private OrganizationDao organizationDao;
    @Autowired
    public TaskServiceImpl(TaskDao taskDao, TaskDtoMapper taskDtoMapper,
                           TransactionDao transDao, BudgetDao budgetDao,
                           UserDao userDao, UserOrganizationDao userOrganizationDao,
                           OrganizationDao organizationDao) {
        this.taskDao = taskDao;
        this.taskDtoMapper = taskDtoMapper;
        this.transDao = transDao;
        this.budgetDao = budgetDao;
        this.userDao = userDao;
        this.userOrganizationDao = userOrganizationDao;
        this.organizationDao = organizationDao;
    }

    @Override
    @Transactional
    public TaskDto create(TaskDto task) {
        TaskNotificationDto notification = new TaskNotificationDto();
        Task taskResult = taskDao.create(taskDtoMapper.mapDto(task));
        notification.setTitle(task.getTitle());
        notification.setOrgName(organizationDao.findById(userOrganizationDao.findOrgIdById(task.getUsersOrganizationsId())).getName());
        if(task.getApprovedBudget()!=0) {
            notification.setBudget(task.getApprovedBudget());
            String username = userDao.findById(userOrganizationDao.findUserIdById(task.getUsersOrganizationsId())).getUsername();
            transDao.create(new Transaction(null, taskResult.getId(), budgetDao.get().getValue(),
                    task.getApprovedBudget(), null, null));
            Budget budget = new Budget(budgetDao.get().getValue() - taskResult.getApprovedBudget());
            budgetDao.createOrUpdate(budget);
            simpMessagingTemplate.convertAndSend( "/topic/task.create/" + username, notification);
        } else {
            notification.setBudget(task.getBudget());
            simpMessagingTemplate.convertAndSend("/topic/task.create", notification);
        }
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
        return userOrganizationDao.findIdByUserIdAndOrgId(userId, orgId);
    }

    private List<TaskDto> mapListDto(List<Task> tasks) {
        return tasks.stream().map(taskDtoMapper::mapRow).collect(Collectors.toList());
    }

    @Autowired
    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
}
