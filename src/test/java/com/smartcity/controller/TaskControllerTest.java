package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartcity.dto.TaskDto;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.interceptor.ExceptionInterceptor;
import com.smartcity.service.TaskService;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private final LocalDateTime dateTest = LocalDateTime.parse(LocalDateTime.now().format(FORMATTER));

    private TaskDto task = new TaskDto(2L, "Santa", "Task for Santa", dateTest, "TODO",
            1000L, 1000L, dateTest, dateTest, 1L);

    private MockMvc mockMvc;

    private final Long fakeId = 5L;
    private final DbOperationException dbOperationException = new DbOperationException("Can't create task");
    private final NotFoundException notFoundException = new NotFoundException("Task with id: " + fakeId + " not found");

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(taskController)
                .setControllerAdvice(ExceptionInterceptor.class)
                .build();
    }

    @Test
    void testCreateTask_failFlow() throws Exception {
        task.setUsersOrganizationsId(3L);
        doThrow(dbOperationException).when(taskService).create(task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String resultJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(resultJson))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("url").value("/tasks"))
                .andExpect(jsonPath("message").value(dbOperationException.getLocalizedMessage()));
    }

    @Test
    void testCreateTask_successFlow() throws Exception {
        doReturn(task).when(taskService).create(task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String resultJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(resultJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(task.getId()))
                .andExpect(jsonPath("title").value(task.getTitle()))
                .andExpect(jsonPath("description").value(task.getDescription()))
                .andExpect(jsonPath("taskStatus").value(task.getTaskStatus()))
                .andExpect(jsonPath("budget").value(task.getBudget()))
                .andExpect(jsonPath("approvedBudget").value(task.getApprovedBudget()))
                .andExpect(jsonPath("usersOrganizationsId").value(task.getUsersOrganizationsId()));
    }

    @Test
    void testFindTaskById_failFlow() throws Exception {
        doThrow(notFoundException).when(taskService).findById(fakeId);

        mockMvc.perform(get("/tasks/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/tasks/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testFindTaskById_successFlow() throws Exception {
        doReturn(task).when(taskService).findById(task.getId());

        mockMvc.perform(get("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(task.getId()))
                .andExpect(jsonPath("title").value(task.getTitle()))
                .andExpect(jsonPath("description").value(task.getDescription()))
                .andExpect(jsonPath("taskStatus").value(task.getTaskStatus()))
                .andExpect(jsonPath("budget").value(task.getBudget()))
                .andExpect(jsonPath("approvedBudget").value(task.getApprovedBudget()))
                .andExpect(jsonPath("usersOrganizationsId").value(task.getUsersOrganizationsId()));
    }

    @Test
    void testDeleteTask_failFlow() throws Exception {
        doThrow(notFoundException).when(taskService).delete(fakeId);

        mockMvc.perform(delete("/tasks/" + fakeId)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/tasks/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testDeleteTask_successFlow() throws Exception {
        doReturn(true).when(taskService).delete(task.getId());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/" + task.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    void testUpdateTaskDbOperationException_failFlow() throws Exception {
        task.setUsersOrganizationsId(6L);
        doThrow(dbOperationException).when(taskService).update(task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String resultJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resultJson))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("url").value("/tasks/" + task.getId()))
                .andExpect(jsonPath("message").value(dbOperationException.getLocalizedMessage()));
    }

    @Test
    void testUpdateTask_successFlow() throws Exception {
        doReturn(task).when(taskService).update(task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String resultJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resultJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(task.getId()))
                .andExpect(jsonPath("title").value(task.getTitle()))
                .andExpect(jsonPath("description").value(task.getDescription()))
                .andExpect(jsonPath("taskStatus").value(task.getTaskStatus()))
                .andExpect(jsonPath("budget").value(task.getBudget()))
                .andExpect(jsonPath("approvedBudget").value(task.getApprovedBudget()))
                .andExpect(jsonPath("usersOrganizationsId").value(task.getUsersOrganizationsId()));
    }

    @Test
    void testFindTaskByOrganizationId_failFlow() throws Exception {
        doThrow(notFoundException).when(taskService).findByOrganizationId(fakeId);

        mockMvc.perform(get("/tasks/findByOrg")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("findByOrganizationId", fakeId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/tasks/findByOrg"))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testFindTaskByOrganizationId_successFlow() throws Exception {
        List<TaskDto> expectedTaskDtoList = Arrays.asList(task);
        doReturn(expectedTaskDtoList).when(taskService).findByOrganizationId(task.getUsersOrganizationsId());

        MvcResult mvcResult = mockMvc.perform(get("/tasks/findByOrg")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("findByOrganizationId", task.getUsersOrganizationsId().toString()))
                .andReturn();

        assertEquals(expectedTaskDtoList.toString(), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testFindTaskByUserId_failFlow() throws Exception {
        doThrow(notFoundException).when(taskService).findByUserId(fakeId);

        mockMvc.perform(get("/tasks/findByUser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("findByUserId", fakeId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/tasks/findByUser"))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testFindTaskByUserId_successFlow() throws Exception {
        List<TaskDto> expectedTaskDtoList = Arrays.asList(task);
        doReturn(expectedTaskDtoList).when(taskService).findByUserId(task.getUsersOrganizationsId());

        MvcResult mvcResult = mockMvc.perform(get("/tasks/findByUser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("findByUserId", task.getUsersOrganizationsId().toString()))
                .andReturn();

        assertEquals(expectedTaskDtoList.toString(), mvcResult.getResponse().getContentAsString());
    }
}
