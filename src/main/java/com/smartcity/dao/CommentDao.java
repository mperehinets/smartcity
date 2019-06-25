package com.smartcity.dao;

import com.smartcity.domain.Comment;
import com.smartcity.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {

    Comment create(Comment comment);

    Comment findById(Long id);

    Comment update(Comment comment);

    boolean delete(Long id);

    List<Comment> findByTaskId(Long id);

    List<Comment> findByUserId(Long id);

    boolean addUserToCommentSeen(Comment comment, User user);

}
