package com.smartcity.mapper;

import com.smartcity.domain.Transaction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransactionMapper implements RowMapper<Transaction> {

    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setTaskId(resultSet.getLong("task_id"));
        transaction.setCurrentBudget(resultSet.getLong("current_budget"));
        transaction.setTransactionBudget(resultSet.getLong("transaction_budget"));
        transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
        transaction.setUpdatedDate(resultSet.getTimestamp("updated_date").toLocalDateTime());
        return transaction;
    }
}