package com.smartcity.service;


import com.smartcity.config.ProfileConfig;
import com.smartcity.dao.CommentDao;
import com.smartcity.domain.Comment;
import com.smartcity.dto.CommentDto;
import com.smartcity.mapperDto.CommentDtoMapper;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    private CommentDto commentDto = new CommentDto(2L, "Comment for Santa",
            LocalDateTime.now(),
            LocalDateTime.now(),
            1L, 1L);

    @Mock
    private CommentDao commentDao;

    private CommentDtoMapper commentDtoMapper = new CommentDtoMapper();

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(commentDao, commentDtoMapper);
        comment = commentDtoMapper.commentDtoToComment(commentDto);
    }

    @Test
    public void testCreateComment() {
        doReturn(comment).when(commentDao).create(comment);
        CommentDto result = commentService.create(commentDto);
        assertThat(result).isEqualToIgnoringGivenFields(commentDtoMapper.commentToCommentDto(comment),
                "createdDate", "updatedDate");
    }

    @Test
    public void testFindCommentById() {
        doReturn(comment).when(commentDao).findById(comment.getId());
        CommentDto result = commentService.findById(commentDto.getId());
        assertThat(result).isEqualToIgnoringGivenFields(commentDtoMapper.commentToCommentDto(comment),
                "createdDate", "updatedDate");
    }

    @Test
    public void testFindCommentByUserId() {
        List<Comment> commentList = Arrays.asList(comment);
        List<CommentDto> commentDtoList = Arrays.asList(commentDtoMapper.commentToCommentDto(comment));
        doReturn(commentList).when(commentDao).findByUserId(comment.getUserId());
        List<CommentDto> result = commentService.findByUserId(commentDto.getUserId());
        assertEquals(result, commentDtoList);
    }

    @Test
    public void testFindCommentByTaskId() {
        List<Comment> commentList = Arrays.asList(comment);
        List<CommentDto> commentDtoList = Arrays.asList(commentDtoMapper.commentToCommentDto(comment));
        doReturn(commentList).when(commentDao).findByTaskId(comment.getTaskId());
        List<CommentDto> result = commentService.findByTaskId(commentDto.getTaskId());
        assertEquals(result, commentDtoList);
    }


    @Test
    public void testUpdateComment() {
        doReturn(comment).when(commentDao).update(comment);
        CommentDto result = commentService.update(commentDto);
        assertThat(result).isEqualToIgnoringGivenFields(commentDtoMapper.commentToCommentDto(comment),
                "createdDate", "updatedDate");
    }

    @Test
    public void testDeleteComment() {
        doReturn(true).when(commentDao).delete(comment.getId());
        boolean result = commentService.delete(commentDto.getId());
        assertTrue(result);
    }
}