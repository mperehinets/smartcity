package com.smartcity.service;

import com.smartcity.dto.TaskDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    TaskDto create(TaskDto task);

    TaskDto findById(Long id);

    List<TaskDto> findByOrganizationId(Long id);

    List<TaskDto> findByUserId(Long id);

    List<TaskDto> findByDate(Long id, LocalDateTime from, LocalDateTime to);

    TaskDto update(TaskDto task);

    boolean delete(Long id);

    Long findUsersOrgIdByUserIdAndOrgId(Long userId, Long orgId);
}
