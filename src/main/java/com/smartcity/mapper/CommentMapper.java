package com.smartcity.mapper;

import com.smartcity.domain.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CommentMapper implements RowMapper<Comment> {

    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong("id"));
        comment.setDescription(resultSet.getString("description"));
        comment.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
        comment.setUpdatedDate(resultSet.getTimestamp("updated_date").toLocalDateTime());
        comment.setUserId(resultSet.getLong("user_id"));
        comment.setTaskId(resultSet.getLong("task_id"));
        return comment;
    }
}
