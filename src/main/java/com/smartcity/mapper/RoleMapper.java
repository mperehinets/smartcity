package com.smartcity.mapper;

import com.smartcity.domain.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class RoleMapper implements RowMapper<Role> {

    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        role.setName(resultSet.getString("name"));
        role.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        role.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
        return role;
    }
}
