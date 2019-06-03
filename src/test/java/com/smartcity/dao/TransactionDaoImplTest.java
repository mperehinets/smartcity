package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionDaoImplTest extends BaseTest {

    private Transaction transaction;

    @BeforeEach
    void init() {
        transaction = new Transaction(2L, 1L,
                5000L, 3000L,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Autowired
    private TransactionDao transDao;

    @Test
    void testCreateTransaction() {
        assertThat(transDao.create(transaction)).isEqualToIgnoringGivenFields(transaction,
                "createdDate", "updatedDate");
    }

    @Test
    void testCreateTransaction_invalidTaskId() {
        transaction.setTaskId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> transDao.create(transaction));
    }

    @Test
    void testCreateTransaction_missingTaskId() {
        transaction.setTaskId(null);
        assertThrows(DbOperationException.class, () -> transDao.create(transaction));
    }

    @Test
    void testFindTransaction() {
        transDao.create(transaction);
        assertThat(transaction).
                isEqualToIgnoringGivenFields(transDao.findById(transaction.getId()),
                        "createdDate", "updatedDate");
    }

    @Test
    void testFindTransaction_invalidId() {
        assertThrows(NotFoundException.class, () -> transDao.findById(null));
        assertThrows(NotFoundException.class, () -> transDao.findById(Long.MAX_VALUE));
    }

    @Test
    void testFindTransactionsByTaskId() {
        transDao.create(transaction);
        assertThat(transaction).isEqualToIgnoringGivenFields(
                transDao.findByTaskId(transaction.getTaskId()).get(0),
                "createdDate", "updatedDate");
    }

    @Test
    void testFindTransactionsByTaskId_amountOfTransactions() {
        List<Transaction> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            transaction.setId((long) i);
            transDao.create(transaction);
            list.add(transaction);
            assertThat(list.get(i - 1)).isEqualToIgnoringGivenFields(
                    transDao.findByTaskId(transaction.getTaskId()).get(i - 1),
                    "createdDate", "updatedDate");
        }
    }

    @Test
    void testFindTransactionsByTaskId_emptyList() {
        assertThat(transDao.findByTaskId(Long.MAX_VALUE)).isEmpty();
    }

    @Test
    void testUpdateTransaction() {
        transDao.create(transaction);
        Transaction updatedTransaction = new Transaction(1L, 1L,
                800000L, 44000L,
                LocalDateTime.now(), LocalDateTime.now());
        transDao.update(updatedTransaction);
        assertThat(transDao.findById(updatedTransaction.getId())).isEqualToIgnoringGivenFields(updatedTransaction,
                "createdDate", "updatedDate");
    }

    @Test
    void testUpdateTransaction_invalidId() {
        Transaction newTransaction = new Transaction(500L, 1L,
                800000L, 44000L,
                LocalDateTime.now(), LocalDateTime.now());
        assertThrows(NotFoundException.class, () -> transDao.update(newTransaction));
    }

    @Test
    void testDeleteTransaction() {
        transDao.create(transaction);
        assertTrue(transDao.delete(1L));
    }

    @Test
    void testDeleteTransaction_invalidId() {
        assertThrows(NotFoundException.class, () -> transDao.delete(Long.MAX_VALUE));
    }

    @AfterEach
    void cleanTransactions() {
        clearTables("Transactions");
    }
}