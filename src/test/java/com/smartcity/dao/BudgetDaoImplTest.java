package com.smartcity.dao;

import com.smartcity.domain.Budget;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BudgetDaoImplTest extends BaseTest {

    @Autowired
    private BudgetDaoImpl budgetDao;
    private Budget budget = new Budget(10000L);

    @Test
    void testCreateBudget() {
        assertEquals(budgetDao.createOrUpdate(budget), budget);
    }

    @Test
    void testGetBudget() {
        budgetDao.createOrUpdate(budget);

        Budget created = budgetDao.get();

        assertEquals(created, budget);
    }

    @Test
    void testGetBudget_noRowsFound() {
        assertThrows(NotFoundException.class, () -> budgetDao.get());
    }

    @Test
    void testUpdateBudget() {
        budgetDao.createOrUpdate(budget);
        budget.setValue(20000L);
        budgetDao.createOrUpdate(budget);
        Budget updated = budgetDao.get();
        assertEquals(updated, budget);
    }

    @AfterEach
    void afterEach() {
        clearTables("Budget");
    }
}