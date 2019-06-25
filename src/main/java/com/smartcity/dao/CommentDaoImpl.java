package com.smartcity.dao;

import com.smartcity.domain.Comment;
import com.smartcity.domain.User;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoImpl implements CommentDao {

    private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);
    private JdbcTemplate jdbcTemplate;
    private CommentMapper mapper;

    @Autowired
    public CommentDaoImpl(DataSource dataSource, CommentMapper mapper) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.mapper = mapper;
    }

    @Override
    public Comment create(Comment comment) {
        try {
            LocalDateTime createdDate = LocalDateTime.now();
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            comment.setUpdatedDate(createdDate);
            comment.setCreatedDate(createdDate);
            jdbcTemplate.update(con -> createStatement(comment, con), holder);
            comment.setId(Optional.ofNullable(holder.getKey()).map(Number::longValue)
                    .orElseThrow(() -> new DbOperationException("Create comment Dao method error: " +
                            "Autogenerated key is null")));
            return comment;
        } catch (Exception e) {
            logger.error("Can't create Comment:{}.Error:{}", comment, e.getMessage());
            throw new DbOperationException("Can't create comment by id=" + comment.getId() +
                    "Comment:" + comment);
        }
    }

    @Override
    public Comment findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(Queries.SQL_COMMENT_GET_BY_ID, mapper, id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Comment not found.Can't get comment by id={}. Error:", id, ex.getMessage());
            throw new NotFoundException("Comment not found.Can't get comment by id=" + id);
        } catch (Exception e) {
            logger.error("Can't get comment by id={}. Error: ", id, e);
            throw new DbOperationException("Can't get comment with id=" + id);
        }
    }

    @Override
    public Comment update(Comment comment) {
        int rowsAffected;
        try {
            LocalDateTime updatedDate = LocalDateTime.now();
            rowsAffected = jdbcTemplate.update(Queries.SQL_COMMENT_UPDATE,
                    comment.getDescription(),
                    updatedDate,
                    comment.getUserId(),
                    comment.getTaskId(),
                    comment.getId()
            );
            comment.setUpdatedDate(updatedDate);
        } catch (Exception e) {
            logger.error("Update comment (id = {}) exception. Message: {}", comment.getId(), e.getMessage());
            throw new DbOperationException("Update comment exception");
        }
        if (rowsAffected < 1) {
            throw loggedNotFoundException(comment.getId());
        }
        return comment;
    }

    @Override
    public boolean delete(Long id) {
        int rowsAffected;
        try {
            rowsAffected = jdbcTemplate.update(Queries.SQL_COMMENT_DELETE, id);
        } catch (Exception e) {
            logger.error("Delete comment (id = {}) exception. Message: {}", id, e.getMessage());
            throw new DbOperationException("Delete comment exception");
        }
        if (rowsAffected < 1) throw loggedNotFoundException(id);
        else return true;
    }

    @Override
    public List<Comment> findByTaskId(Long id) {
        List<Comment> list;
        try {
            list = jdbcTemplate.query(Queries.SQL_COMMENT_GET_BY_TASK_ID,
                    mapper, id);
        } catch (Exception e) {
            logger.error("Get comments by task id (task id = {}) exception. Message: {}",
                    id, e.getMessage());
            throw new DbOperationException("Get comment exception");
        }
        return list;
    }

    @Override
    public List<Comment> findByUserId(Long id) {
        List<Comment> list;
        try {
            list = jdbcTemplate.query(Queries.SQL_COMMENT_GET_BY_USER_ID,
                    mapper, id);
        } catch (Exception e) {
            logger.error("Get comments by user id (user id = {}) exception. Message: {}",
                    id, e.getMessage());
            throw new DbOperationException("Get comment exception");
        }
        return list;
    }

    @Override
    public boolean addUserToCommentSeen(Comment comment, User user) {
        try {
            jdbcTemplate.update(Queries.SQL_COMMENT_ADD_USER_TO_COMMENTSEEN, comment.getId(), user.getId()
            );
            return true;
        } catch (Exception e) {
            logger.error("Can't add user:{} to commentSeen:{}. Error:{}", user, comment, e.getMessage());
            throw new DbOperationException("Can't add user to commentSeen." +
                    " addUserToCommentSeen comment Dao method error:" + e);
        }
    }


    private NotFoundException loggedNotFoundException(Long id) {
        NotFoundException notFoundException = new NotFoundException("Comment not found by Id = " + id);
        logger.error("Runtime exception. Comment by id = {} not found. Message: {}",
                id, notFoundException.getMessage());
        return notFoundException;
    }

    private PreparedStatement createStatement(Comment comment, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                Queries.SQL_COMMENT_CREATE,
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, comment.getDescription());
        ps.setObject(2, comment.getCreatedDate());
        ps.setObject(3, comment.getUpdatedDate());
        ps.setLong(4, comment.getUserId());
        ps.setLong(5, comment.getTaskId());
        return ps;
    }

    class Queries {
        static final String SQL_COMMENT_CREATE = "INSERT INTO Comments(description, created_date, updated_date, user_id, task_id)" +
                " VALUES (?,?,?,?,?)";

        static final String SQL_COMMENT_ADD_USER_TO_COMMENTSEEN = "INSERT INTO SeenComments(commentId, userId)" +
                " VALUES (?,?)";

        static final String SQL_COMMENT_GET_BY_ID = "Select * from Comments where id = ?";

        static final String SQL_COMMENT_UPDATE = "Update Comments set description = ? ,updated_date = ?, user_id = ?, " +
                "task_id = ? where id = ?";
        static final String SQL_COMMENT_DELETE = "Delete from Comments where id = ?";

        static final String SQL_COMMENT_GET_BY_TASK_ID = "Select * from Comments where task_id = ?";

        static final String SQL_COMMENT_GET_BY_USER_ID = "Select * from Comments where user_id = ?";

    }
}
