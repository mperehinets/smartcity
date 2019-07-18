package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartcity.dto.CommentDto;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.interceptor.ExceptionInterceptor;
import com.smartcity.service.CommentService;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    private final ObjectMapper objMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private CommentDto commentDto;

    private MockMvc mockMvc;

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private final LocalDateTime dateTest = LocalDateTime.parse(LocalDateTime.now().format(FORMATTER));

    private final Long fakeId = 5L;
    private final DbOperationException dbOperationException = new DbOperationException("Can't create task");
    private final NotFoundException notFoundException = new NotFoundException("Task with id: " + fakeId + " not found");


    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(commentController)
                .setControllerAdvice(ExceptionInterceptor.class)
                .build();
        commentDto = new CommentDto(2L, "Comment for Santa",
                dateTest,
                dateTest,
                1L, 1L,null);
    }

    @Test
    void testCreateComment_failFlow() throws Exception {
        commentDto.setTaskId(fakeId);
        Mockito.when(commentService.create(commentDto))
                .thenThrow(dbOperationException);

        String json = objMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("url").value("/comments"))
                .andExpect(jsonPath("message").value(dbOperationException.getLocalizedMessage()));
    }

    @Test
    void testCreateComment_successFlow() throws Exception {

        Mockito.when(commentService.create(commentDto)).thenReturn(commentDto);

        String json = objMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(commentDto.getId()))
                .andExpect(jsonPath("description").value(commentDto.getDescription()))
                .andExpect(jsonPath("taskId").value(commentDto.getTaskId()))
                .andExpect(jsonPath("userId").value(commentDto.getUserId()));
    }

    @Test
    void testUpdateComment_failFlow() throws Exception {
        CommentDto updatedComments = new CommentDto(1L, "Description for Task $2", null, null, fakeId, 1L,null);
        Mockito.when(commentService.update(updatedComments))
                .thenThrow(dbOperationException);
        String json = objMapper.writeValueAsString(updatedComments);
        mockMvc.perform(put("/comments/" + updatedComments.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("url").value("/comments/" + updatedComments.getId()))
                .andExpect(jsonPath("message").value(dbOperationException.getLocalizedMessage()));
    }

    @Test
    void testUpdateComment_successFlow() throws Exception {
        CommentDto updatedComments = new CommentDto(1L, "Description for Task $2", null, null, 1L, 1L,null);
        Mockito.when(commentService.update(updatedComments)).thenReturn(updatedComments);
        String json = objMapper.writeValueAsString(updatedComments);
        mockMvc.perform(put("/comments/" + updatedComments.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updatedComments.getId()))
                .andExpect(jsonPath("taskId").value(updatedComments.getTaskId()))
                .andExpect(jsonPath("userId").value(updatedComments.getUserId()))
                .andExpect(jsonPath("description").value(updatedComments.getDescription()))
                .andExpect(jsonPath("userSeen").value(updatedComments.getUserSeen()));
    }

    @Test
    void testDeleteComment_failFlow() throws Exception {
        Mockito.when(commentService.delete(fakeId))
                .thenThrow(notFoundException);
        String json = objMapper.writeValueAsString(commentDto);
        mockMvc.perform(delete("/comments/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON).content(json)
        )
                .andExpect(status().isNotFound()).andExpect(jsonPath("url").value("/comments/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testDeleteComment_successFlow() throws Exception {
        Mockito.when(commentService.delete(commentDto.getId())).thenReturn(true);
        String json = objMapper.writeValueAsString(commentDto);
        mockMvc.perform(delete("/comments/" + commentDto.getId(),commentDto)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByIdComment_failFlow() throws Exception {
        Mockito.when(commentService.findById(fakeId))
                .thenThrow(notFoundException);
        mockMvc.perform(get("/comments/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().isNotFound()).andExpect(jsonPath("url").value("/comments/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testFindByIdComment_successFlow() throws Exception {
        Mockito.when(commentService.findById(commentDto.getId())).thenReturn(commentDto);
        mockMvc.perform(get("/comments/" + commentDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(commentDto.getId()))
                .andExpect(jsonPath("description").value(commentDto.getDescription()))
                .andExpect(jsonPath("taskId").value(commentDto.getTaskId()))
                .andExpect(jsonPath("userId").value(commentDto.getUserId()));
    }

    @Test
    void testFindByTaskdIdComment_failFlow() throws Exception {
        Mockito.when(commentService.findByTaskId(fakeId))
                .thenThrow(notFoundException);
        mockMvc.perform(get("/comments/taskId/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().isNotFound()).andExpect(jsonPath("url").value("/comments/taskId/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testFindByTaskdIdComment_successFlow() throws Exception {
        List<CommentDto> startList = Collections.singletonList(commentDto);
        Mockito.when(commentService.findByTaskId(commentDto.getTaskId())).thenReturn(startList);
        final MvcResult result = mockMvc.perform(get("/comments/taskId/" + commentDto.getTaskId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        CommentDto[] arrCommentDto = objMapper
                .readValue(result.getResponse().getContentAsString(), CommentDto[].class);
        List<CommentDto> resList = Arrays.asList(arrCommentDto);
        assertEquals(startList, resList);
    }

    @Test
    void testFindByUserIdComment_failFlow() throws Exception {
        Mockito.when(commentService.findByUserId(fakeId))
                .thenThrow(notFoundException);
        mockMvc.perform(get("/comments/userId/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/comments/userId/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void testFindByUserIdComment_successFlow() throws Exception {
        List<CommentDto> startList = Collections.singletonList(commentDto);
        Mockito.when(commentService.findByUserId(commentDto.getUserId())).thenReturn(startList);
        final MvcResult result = mockMvc.perform(get("/comments/userId/" + commentDto.getUserId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        CommentDto[] arrCommentDto = objMapper
                .readValue(result.getResponse().getContentAsString(), CommentDto[].class);
        List<CommentDto> resList = Arrays.asList(arrCommentDto);
        assertEquals(startList, resList);
    }

}
