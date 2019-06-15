package com.smartcity.controller;

import com.smartcity.dto.TaskDto;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;
import com.smartcity.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getTaskControllerCreateTask())")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaskDto createTask(@Validated(NewRecord.class) @RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto findById(@PathVariable("id") Long id) {
        return taskService.findById(id);
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getTaskControllerUpdateTask())")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto updateTask(
            @Validated(ExistingRecord.class)
            @PathVariable("id") Long id,
            @RequestBody TaskDto taskDto) {
        taskDto.setId(id);
        return taskService.update(taskDto);
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getTaskControllerDeleteTask())")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable("id") Long id) {
        return taskService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/organizationId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDto> findByOrganizationId(@PathVariable("id") Long id) {
        return taskService.findByOrganizationId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/userId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDto> findByUserId(@PathVariable("id") Long id) {
        return taskService.findByUserId(id);
    }
}
