package com.smartcity.dao;

import com.smartcity.domain.Comment;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommentDaoImplTest extends BaseTest {

    private Comment comment = new Comment(
            1L, "Comment for comment",
            LocalDateTime.now(), LocalDateTime.now(),
            1L, 1L
    );

    @Autowired
    private CommentDao commentDao;

    @Test
    void testCreateComment() {

        assertEquals(comment, commentDao.create(comment));

    }

    @Test
    void testCreateComment_omittedNotNullFields() {
        Comment emptyComment = new Comment();
        assertThrows(DbOperationException.class, () -> commentDao.create(emptyComment));

    }

    @Test
    void testCreateComment_invalidTaskId() {
        comment.setTaskId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> commentDao.create(comment));
    }

    @Test
    void testCreateComment_missingTaskId() {
        comment.setTaskId(null);
        assertThrows(DbOperationException.class, () -> commentDao.create(comment));
    }

    @Test
    void testCreateComment_invalidUserId() {
        comment.setUserId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> commentDao.create(comment));
    }

    @Test
    void testCreateComment_missingUserId() {
        comment.setUserId(null);
        assertThrows(DbOperationException.class, () -> commentDao.create(comment));
    }

    @Test
    void testFindComment() {
        commentDao.create(comment);
        Comment result = commentDao.findById(comment.getId());
        assertThat(comment).
                isEqualToIgnoringGivenFields(result,
                        "createdDate", "updatedDate");
    }

    @Test
    void testFindComment_invalidId() {
        assertThrows(NotFoundException.class, () -> commentDao.findById(Long.MAX_VALUE));

    }

    @Test
    void testUpdateComment() {
        commentDao.create(comment);

        Comment updatedComment = new Comment(comment.getId(), "Comment for Test$2",
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L, 1L);

        commentDao.update(updatedComment);
        Comment resultComment = commentDao.findById(updatedComment.getId());
        assertThat(updatedComment).isEqualToIgnoringGivenFields(resultComment,
                "createdDate", "updatedDate");
    }

    @Test
    void testUpdateComment_invalidId() {
        Comment updatedComment = new Comment(Long.MAX_VALUE, "Comment for Test",
                LocalDateTime.now(),
                LocalDateTime.now(),
                800000L, 44000L);

        assertThrows(NotFoundException.class, () -> commentDao.update(updatedComment));

    }

    @Test
    void testDeleteComment() {
        commentDao.create(comment);
        assertTrue(commentDao.delete(comment.getId()));
    }

    @Test
    void testDeleteComment_invalidId() {
        assertThrows(NotFoundException.class, () -> commentDao.delete(Long.MAX_VALUE));

    }

    @Test
    void testFindCommentByTaskId() {
        commentDao.create(comment);
        assertThat(comment).isEqualToIgnoringGivenFields(commentDao.findByTaskId(1L).get(0),
                "createdDate", "updatedDate");
    }

    @Test
    void testFindCommentByUserId() {
        commentDao.create(comment);
        assertThat(comment).isEqualToIgnoringGivenFields(commentDao.findByTaskId(1L).get(0),
                "createdDate", "updatedDate");
    }

    @Test
    void testFindCommentByTaskId_nullList() {
        assertThat(commentDao.findByTaskId(Long.MAX_VALUE)).isEmpty();
    }

    @Test
    void testFindCommentByUserId_nullList() {
        assertThat(commentDao.findByUserId(Long.MAX_VALUE)).isEmpty();
    }

    @Test
    void testFindCommentByTaskId_amountOfComment() {
        List<Comment> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            comment.setId((long) i);
            commentDao.create(comment);
            list.add(comment);
            assertThat(list.get(i - 1)).isEqualToIgnoringGivenFields(commentDao.findByTaskId(1L).get(i - 1),
                    "createdDate", "updatedDate");
        }
    }

    @Test
    void testFindCommentByUserId_amountOfComment() {
        List<Comment> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            comment.setId((long) i);
            commentDao.create(comment);
            list.add(comment);
            assertThat(list.get(i - 1)).isEqualToIgnoringGivenFields(commentDao.findByUserId(1L).get(i - 1),
                    "createdDate", "updatedDate");
        }
    }

    @AfterEach
    void afterEach() {
        clearTables("Comments");
    }
}
