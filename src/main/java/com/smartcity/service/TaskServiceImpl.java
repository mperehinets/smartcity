package com.smartcity.service;

import com.smartcity.dao.TaskDao;
import com.smartcity.domain.Task;
import com.smartcity.dto.TaskDto;
import com.smartcity.mapperDto.TaskDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskDao taskDao;
    private TaskDtoMapper taskDtoMapper;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao, TaskDtoMapper taskDtoMapper) {
        this.taskDao = taskDao;
        this.taskDtoMapper = taskDtoMapper;
    }

    @Override
    public TaskDto create(TaskDto task) {
        return taskDtoMapper.mapRow(taskDao.create(taskDtoMapper.mapDto(task)));
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
    public List<TaskDto> findByDate(LocalDateTime from, LocalDateTime to) {
        List<Task> tasks = taskDao.findByDate(from, to);
        return mapListDto(tasks);
    }

    @Override
    public TaskDto update(TaskDto task) {
        return taskDtoMapper.mapRow(taskDao.update(taskDtoMapper.mapDto(task)));
    }

    @Override
    public boolean delete(Long id) {
        return taskDao.delete(id);
    }

    private List<TaskDto> mapListDto(List<Task> tasks) {
        return tasks.stream().map(taskDtoMapper::mapRow).collect(Collectors.toList());
    }
}
