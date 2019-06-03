package com.smartcity.mapperDto;

import com.smartcity.domain.Comment;
import com.smartcity.dto.CommentDto;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CommentDtoMapperTest {

    private Comment comment;
    private CommentDto commentDto;

    private CommentDtoMapper commentDtoMapper = new CommentDtoMapper();


    @BeforeEach
    void setUp() {

        // Initializing comment object
        comment = new Comment();
        comment.setId(1L);
        comment.setDescription("Description");
        comment.setUserId(1L);
        comment.setTaskId(1L);
        comment.setCreatedDate(LocalDateTime.now());
        comment.setUpdatedDate(LocalDateTime.now());

        //Initializing commentDto object
        commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setDescription(comment.getDescription());
        commentDto.setUserId(comment.getUserId());
        commentDto.setTaskId(comment.getTaskId());
        commentDto.setCreatedDate(comment.getCreatedDate());
        commentDto.setUpdatedDate(comment.getUpdatedDate());
    }

    @Test
    void convertCommentIntoCommentDto() {
        CommentDto resultCommentDto = commentDtoMapper.commentToCommentDto(comment);
        assertThat(comment).isEqualToIgnoringGivenFields(resultCommentDto);

    }

    @Test
    void convertCommentDtoIntoComments() {
        Comment resultComment = commentDtoMapper.commentDtoToComment(commentDto);
        assertEquals(comment, resultComment);

    }

}
