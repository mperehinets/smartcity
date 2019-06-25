package com.smartcity.service;


import com.smartcity.dao.CommentDao;
import com.smartcity.dao.UserDao;
import com.smartcity.domain.Comment;
import com.smartcity.dto.CommentDto;
import com.smartcity.mapperDto.CommentDtoMapper;
import com.smartcity.mapperDto.UserDtoMapper;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
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
            1L, 1L,null);

    @Mock
    private CommentDao commentDao;

    @Mock
    private UserDao userDao;

    private CommentDtoMapper commentDtoMapper = new CommentDtoMapper();
    private UserDtoMapper userDtoMapper = new UserDtoMapper();

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(commentDao, commentDtoMapper,userDtoMapper,userDao);
        comment = commentDtoMapper.commentDtoToComment(commentDto);
    }

    @Test
    void testCreateComment() {
        doReturn(comment).when(commentDao).create(comment);
        CommentDto result = commentService.create(commentDto);
        assertThat(result).isEqualToIgnoringGivenFields(commentDtoMapper.commentToCommentDto(comment),
                "createdDate", "updatedDate","userSeen");
    }

    @Test
    void testFindCommentById() {
        doReturn(comment).when(commentDao).findById(comment.getId());
        CommentDto result = commentService.findById(commentDto.getId());
        assertThat(result).isEqualToIgnoringGivenFields(commentDtoMapper.commentToCommentDto(comment),
                "createdDate", "updatedDate","userSeen");
    }

    @Test
    void testFindCommentByUserId() {
        List<Comment> commentList = Collections.singletonList(comment);
        List<CommentDto> commentDtoList = Collections.singletonList(commentDtoMapper.commentToCommentDto(comment));
        doReturn(commentList).when(commentDao).findByUserId(comment.getUserId());
        List<CommentDto> result = commentService.findByUserId(commentDto.getUserId());
        assertEquals(result, commentDtoList);
    }

    @Test
    void testFindCommentByTaskId() {
        List<Comment> commentList = Collections.singletonList(comment);
        List<CommentDto> commentDtoList = Collections.singletonList(commentDtoMapper.commentToCommentDto(comment));
        doReturn(commentList).when(commentDao).findByTaskId(comment.getTaskId());
        List<CommentDto> result = commentService.findByTaskId(commentDto.getTaskId());
        assertEquals(result, commentDtoList);
    }


    @Test
    void testUpdateComment() {
        doReturn(comment).when(commentDao).update(comment);
        CommentDto result = commentService.update(commentDto);
        assertThat(result).isEqualToIgnoringGivenFields(commentDtoMapper.commentToCommentDto(comment),
                "createdDate", "updatedDate","userSeen");
    }

    @Test
    void testDeleteComment() {
        doReturn(true).when(commentDao).delete(comment.getId());
        boolean result = commentService.delete(commentDto.getId());
        assertTrue(result);
    }
}