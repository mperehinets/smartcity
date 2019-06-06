package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartcity.dto.RoleDto;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.interceptor.ExceptionInterceptor;
import com.smartcity.service.RoleService;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private final LocalDateTime dateTest = LocalDateTime.parse(LocalDateTime.now().format(FORMATTER));

    private RoleDto roleDto;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Long fakeId = 5L;

    private final DbOperationException dbOperationException = new DbOperationException("Can't create role");
    private final NotFoundException notFoundException = new NotFoundException("Role with id: " + fakeId + " not found");

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void init() {
        roleDto = new RoleDto(2L, "USER", dateTest, dateTest);
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .setControllerAdvice(ExceptionInterceptor.class)
                .build();
    }

    @Test
    public void testCreateRole_successfulFlow() throws Exception {
        doReturn(roleDto).when(roleService).create(roleDto);


        objectMapper.registerModule(new JavaTimeModule());

        String resultJSON = objectMapper.writeValueAsString(roleDto);
        mockMvc.perform(post("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(resultJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(roleDto.getId()))
                .andExpect(jsonPath("name").value(roleDto.getName()));
    }

    @Test
    public void testFindRoleById_successFlow() throws Exception {
        doReturn(roleDto).when(roleService).findById(roleDto.getId());
        mockMvc.perform(get("/roles/" + roleDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(roleDto.getId()))
                .andExpect(jsonPath("name").value(roleDto.getName()));
    }

    @Test
    public void testFindRoleById_failFlow() throws Exception {
        doThrow(notFoundException).when(roleService).findById(fakeId);

        mockMvc.perform(get("/roles/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/roles/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    public void testGetAllRoles_successFlow() throws Exception {
        List<RoleDto> roleDtos = new ArrayList<>();

        RoleDto roleDto1 = new RoleDto(3L, "ADMIN", LocalDateTime.now(), LocalDateTime.now());

        roleDtos.add(roleDto);
        roleDtos.add(roleDto1);

        doReturn(roleDtos).when(roleService).findAll();

        mockMvc.perform(get("/roles/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(roleDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(roleDtos.get(0).getName()))
                .andExpect(jsonPath("$[1].id").value(roleDtos.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(roleDtos.get(1).getName()));
    }

}
