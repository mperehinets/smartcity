package com.smartcity.mapper;

import com.smartcity.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setSurname(resultSet.getString("surname"));
        user.setName(resultSet.getString("name"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setActive(resultSet.getBoolean("active"));
        user.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        user.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
        return user;
    }
}
