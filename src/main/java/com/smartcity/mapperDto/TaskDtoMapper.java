package com.smartcity.mapperDto;

import com.smartcity.domain.Task;
import com.smartcity.dto.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoMapper {

    public TaskDto mapRow(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setDeadlineDate(task.getDeadlineDate());
        taskDto.setTaskStatus(task.getTaskStatus());
        taskDto.setBudget(task.getBudget());
        taskDto.setApprovedBudget(task.getApprovedBudget());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setUpdatedAt(task.getUpdatedAt());
        taskDto.setUsersOrganizationsId(task.getUsersOrganizationsId());
        return taskDto;
    }

    public Task mapDto(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDeadlineDate(taskDto.getDeadlineDate());
        task.setTaskStatus(taskDto.getTaskStatus());
        task.setBudget(taskDto.getBudget());
        task.setApprovedBudget(taskDto.getApprovedBudget());
        task.setCreatedAt(taskDto.getCreatedAt());
        task.setUpdatedAt(taskDto.getUpdatedAt());
        task.setUsersOrganizationsId(taskDto.getUsersOrganizationsId());
        return task;
    }
}
