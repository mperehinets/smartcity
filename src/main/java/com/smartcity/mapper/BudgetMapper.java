package com.smartcity.mapper;

import com.smartcity.domain.Budget;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BudgetMapper implements RowMapper<Budget> {

    public Budget mapRow(ResultSet resultSet, int i) throws SQLException {
        Budget budget = new Budget();
        budget.setValue(resultSet.getLong("value"));
        return budget;

    }
}
