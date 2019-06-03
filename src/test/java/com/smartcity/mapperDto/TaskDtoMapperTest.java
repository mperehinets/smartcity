package com.smartcity.mapperDto;

import com.smartcity.domain.Task;
import com.smartcity.dto.TaskDto;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TaskDtoMapperTest {

    private Task task = new Task(1L, "Santa", "Task for Santa",
            LocalDateTime.now(), "TODO",
            1000L, 1000L,
            LocalDateTime.now(), LocalDateTime.now(),
            1L);

    private TaskDto taskDto = new TaskDto(task.getId(), task.getTitle(), task.getDescription(),
            task.getDeadlineDate(), task.getTaskStatus(), task.getBudget(), task.getApprovedBudget(),
            task.getCreatedAt(), task.getUpdatedAt(), task.getUsersOrganizationsId());

    private TaskDtoMapper taskDtoMapper = new TaskDtoMapper();

    @Test
    void testConvertDaoToDto() {
        assertEquals(taskDto, taskDtoMapper.mapRow(task));
    }

    @Test
    void testConvertDtoToDao() {
        assertEquals(task, taskDtoMapper.mapDto(taskDto));
    }
}