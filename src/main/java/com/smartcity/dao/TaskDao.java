package com.smartcity.dao;

import com.smartcity.domain.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskDao {

    Task create(Task task);

    Task findById(Long id);

    List<Task> findByOrganizationId(Long id);

    List<Task> findByUserId(Long id);

    List<Task> findAll();

    List<Task> findByDate(LocalDateTime dateFrom,LocalDateTime to);

    Task update(Task task);

    boolean delete(Long id);
}
