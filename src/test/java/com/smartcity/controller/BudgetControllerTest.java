package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.dto.BudgetDto;
import com.smartcity.service.BudgetService;
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

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BudgetControllerTest {

    private BudgetDto budgetDto;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(budgetController)
                .build();
        budgetDto = new BudgetDto(10L);
    }

    @Test
    public void testPut() throws Exception {
        doReturn(budgetDto).when(budgetService).set(budgetDto);
        String resultJson = objectMapper.writeValueAsString(budgetDto);
        mockMvc.perform(put("/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .content(resultJson)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("value").value(budgetDto.getValue()));
    }

    @Test
    public void testGet_successFlow() throws Exception {
        doReturn(budgetDto).when(budgetService).get();
        mockMvc.perform(get("/budget")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("value").value(budgetDto.getValue()));
    }

    @Test
    public void testGet_failureFlow() throws Exception {
        doReturn(budgetDto).when(budgetService).get();
        mockMvc.perform(get("/budget")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("value").value(budgetDto.getValue()));
    }

}