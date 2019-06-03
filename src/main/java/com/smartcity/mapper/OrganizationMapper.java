package com.smartcity.mapper;

import com.smartcity.domain.Organization;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OrganizationMapper implements RowMapper<Organization> {

    public Organization mapRow(ResultSet resultSet, int i) throws SQLException {
        Organization organization = new Organization();
        organization.setId(resultSet.getLong("id"));
        organization.setName(resultSet.getString("name"));
        organization.setAddress(resultSet.getString("address"));
        organization.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        organization.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
        return organization;
    }
}
