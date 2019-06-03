package com.smartcity.mapper;

import com.smartcity.domain.Task;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class TaskMapper implements RowMapper<Task> {

    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getLong("id"));
        task.setTitle(resultSet.getString("title"));
        task.setDeadlineDate(resultSet.getObject("deadline_date", LocalDateTime.class));
        task.setUpdatedAt(resultSet.getObject("updated_date", LocalDateTime.class));
        task.setCreatedAt(resultSet.getObject("created_date", LocalDateTime.class));
        task.setDescription(resultSet.getString("description"));
        task.setTaskStatus(resultSet.getString("task_status"));
        task.setBudget(resultSet.getLong("budget"));
        task.setApprovedBudget(resultSet.getLong("approved_budget"));
        task.setUsersOrganizationsId((resultSet.getLong("users_organizations_id")));
        return task;
    }
}
