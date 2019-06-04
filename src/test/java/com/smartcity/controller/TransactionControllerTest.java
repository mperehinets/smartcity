package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.dto.TransactionDto;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.interceptor.ExceptionInterceptor;
import com.smartcity.service.TransactionServiceImpl;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    private TransactionServiceImpl transService;

    @InjectMocks
    private TransactionController controller;

    private MockMvc mockMvc;
    private TransactionDto transDto;
    private final ObjectMapper objMapper = new ObjectMapper();

    private final Long fakeId = 5L;
    private final DbOperationException dbOperationException = new DbOperationException("Can't create transaction");
    private final NotFoundException notFoundException = new NotFoundException("Transaction with id: " + fakeId + " not found");

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(ExceptionInterceptor.class)
                .build();
        transDto = new TransactionDto(1L, 1L,
                5000L, 3000L,
                null, null);
    }

    @Test
    void testCreateTransaction_failFlow() throws Exception {
        transDto.setTaskId(fakeId);
        doThrow(dbOperationException).when(transService).create(transDto);
        String json = objMapper.writeValueAsString(transDto);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("url").value("/transactions"))
                .andExpect(jsonPath("message").value(dbOperationException.getLocalizedMessage()));
    }

    @Test
    void testCreateTransaction_successFlow() throws Exception {
        doReturn(transDto).when(transService).create(transDto);
        String json = objMapper.writeValueAsString(transDto);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(transDto.getId()))
                .andExpect(jsonPath("taskId").value(transDto.getTaskId()))
                .andExpect(jsonPath("currentBudget").value(transDto.getCurrentBudget()))
                .andExpect(jsonPath("transactionBudget").value(transDto.getTransactionBudget()));
    }

    @Test
    void testUpdateTransaction_failFlow() throws Exception {
        transDto.setTaskId(fakeId);
        doThrow(dbOperationException).when(transService).update(transDto);
        String json = objMapper.writeValueAsString(transDto);
        mockMvc.perform(put("/transactions/" + transDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("url").value("/transactions/" + transDto.getId()))
                .andExpect(jsonPath("message").value(dbOperationException.getLocalizedMessage()));
    }

    @Test
    void testUpdateTransaction_successFlow() throws Exception {
        TransactionDto updatedTrans = new TransactionDto(1L, 1L, 400L, 200L, null, null);
        doReturn(updatedTrans).when(transService).update(updatedTrans);
        String json = objMapper.writeValueAsString(updatedTrans);
        mockMvc.perform(put("/transactions/" + updatedTrans.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updatedTrans.getId()))
                .andExpect(jsonPath("taskId").value(updatedTrans.getTaskId()))
                .andExpect(jsonPath("currentBudget").value(updatedTrans.getCurrentBudget()))
                .andExpect(jsonPath("transactionBudget").value(updatedTrans.getTransactionBudget()));
        assertEquals(transDto.getId(), updatedTrans.getId());
        assertNotEquals(transDto.getCurrentBudget(), updatedTrans.getCurrentBudget());
        assertNotEquals(transDto.getTransactionBudget(), updatedTrans.getTransactionBudget());
    }

    @Test
    void testDeleteTransaction_failFlow() throws Exception {
        doThrow(notFoundException).when(transService).delete(fakeId);
        mockMvc.perform(delete("/transactions/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/transactions/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testDeleteTransaction_successFlow() throws Exception {
        doReturn(true).when(transService).delete(transDto.getId());
        mockMvc.perform(delete("/transactions/" + transDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByIdTransaction_failFlow() throws Exception {
        doThrow(notFoundException).when(transService).findById(fakeId);
        mockMvc.perform(get("/transactions/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/transactions/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testFindByIdTransaction_succrssFlow() throws Exception {
        doReturn(transDto).when(transService).findById(transDto.getId());
        mockMvc.perform(get("/transactions/" + transDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(transDto.getId()))
                .andExpect(jsonPath("taskId").value(transDto.getTaskId()))
                .andExpect(jsonPath("currentBudget").value(transDto.getCurrentBudget()))
                .andExpect(jsonPath("transactionBudget").value(transDto.getTransactionBudget()));
    }

    @Test
    void testFindByTaskIdTransaction_failFlow() throws Exception {
        doThrow(notFoundException).when(transService).findByTaskId(fakeId);
        mockMvc.perform(get("/transactions/taskId/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/transactions/taskId/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testFindByTaskIdTransaction_successFlow() throws Exception {
        List<TransactionDto> startList = Collections.singletonList(transDto);
        doReturn(startList).when(transService).findByTaskId(transDto.getTaskId());
        final MvcResult result = mockMvc.perform(get("/transactions/taskId/" + transDto.getTaskId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        TransactionDto[] arrTransaction = objMapper
                .readValue(result.getResponse().getContentAsString(), TransactionDto[].class);
        List<TransactionDto> resList = Arrays.asList(arrTransaction);
        assertEquals(startList, resList);
    }

}
