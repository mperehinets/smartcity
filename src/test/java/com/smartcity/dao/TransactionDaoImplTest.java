package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
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
        assertTransactionsEqual(transDao.create(transaction), transaction);
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
        assertTransactionsEqual(transaction, transDao.findById(transaction.getId()));
    }

    @Test
    void testFindTransaction_invalidId() {
        assertThrows(NotFoundException.class, () -> transDao.findById(null));
        assertThrows(NotFoundException.class, () -> transDao.findById(Long.MAX_VALUE));
    }

    @Test
    void testFindTransactionsByTaskId() {
        transDao.create(transaction);
        assertTransactionsEqual(transaction, transDao.findByTaskId(transaction.getTaskId()).get(0));
    }

    @Test
    void testFindTransactionsByTaskId_amountOfTransactions() {
        List<Transaction> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            transaction.setId((long) i);
            transDao.create(transaction);
            list.add(transaction);
            assertTransactionsEqual(list.get(i - 1), transDao.findByTaskId(transaction.getTaskId()).get(i - 1));
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
        assertTransactionsEqual(transDao.findById(updatedTransaction.getId()), updatedTransaction);
    }

    @Test
    void testFindTransactionByDate() {
        cleanTransactions();
        LocalDateTime date = LocalDateTime.now().minusMonths(1L);
        Transaction transaction = new Transaction(3L, 1L, 3000L, 500L, LocalDateTime.now(), LocalDateTime.now());
        transDao.create(this.transaction);
        transDao.create(transaction);
        List<Transaction> initList = asList(this.transaction, transaction);
        List<Transaction> resultList = transDao.findByDate(transaction.getTaskId(), date, LocalDateTime.now());
        IntStream.range(0, resultList.size())
                .mapToObj(i -> new Pair<>(initList.get(i), resultList.get(i)))
                .forEach(t -> assertTransactionsEqual(t.getKey(), t.getValue()));
    }

    private void assertTransactionsEqual(Transaction t1, Transaction t2) {
        assertThat(t1).isEqualToIgnoringGivenFields(t2, "createdDate", "updatedDate");
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
