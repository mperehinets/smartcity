package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartcity.dto.CommentDto;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    private final ObjectMapper objMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private CommentDto commentDto;

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(commentController)
                .build();
        commentDto = new CommentDto(2L, "Comment for Santa",
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L, 1L);
    }

    @Test
    public void testCreateComment() throws Exception {

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
    public void testUpdateComment() throws Exception {
        CommentDto updatedComments = new CommentDto(1L, "Description for Task $2", null, null, 1L, 1L);
        Mockito.when(commentService.update(updatedComments)).thenReturn(updatedComments);
        String json = objMapper.writeValueAsString(updatedComments);
        mockMvc.perform(put("/comments/" + updatedComments.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updatedComments.getId()))
                .andExpect(jsonPath("taskId").value(updatedComments.getTaskId()))
                .andExpect(jsonPath("userId").value(updatedComments.getUserId()))
                .andExpect(jsonPath("description").value(updatedComments.getDescription()));
    }

    @Test
    public void testDeleteComment() throws Exception {
        Mockito.when(commentService.delete(commentDto.getId())).thenReturn(true);
        mockMvc.perform(delete("/comments/" + commentDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdComment() throws Exception {
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
    public void testFindByTaskdIdComment() throws Exception {
        List<CommentDto> startList = Collections.singletonList(commentDto);
        Mockito.when(commentService.findByTaskId(commentDto.getTaskId())).thenReturn(startList);
        final MvcResult result = mockMvc.perform(get("/comments/findByTask")
                .param("findByTaskId", commentDto.getTaskId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        CommentDto[] arrCommentDto = objMapper
                .readValue(result.getResponse().getContentAsString(), CommentDto[].class);
        List<CommentDto> resList = Arrays.asList(arrCommentDto);
        assertEquals(startList, resList);
    }

    @Test
    public void testFindByUserIdComment() throws Exception {
        List<CommentDto> startList = Collections.singletonList(commentDto);
        Mockito.when(commentService.findByUserId(commentDto.getUserId())).thenReturn(startList);
        final MvcResult result = mockMvc.perform(get("/comments/findByUser")
                .param("findByUserId", commentDto.getUserId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        CommentDto[] arrCommentDto = objMapper
                .readValue(result.getResponse().getContentAsString(), CommentDto[].class);
        List<CommentDto> resList = Arrays.asList(arrCommentDto);
        assertEquals(startList, resList);
    }

}
