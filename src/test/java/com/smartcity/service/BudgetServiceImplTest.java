package com.smartcity.service;

import com.smartcity.dao.BudgetDao;
import com.smartcity.domain.Budget;
import com.smartcity.dto.BudgetDto;
import com.smartcity.mapperDto.BudgetDtoMapper;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BudgetServiceImplTest {

    private final BudgetDto budgetDto = new BudgetDto(1L);

    private final BudgetDtoMapper budgetDtoMapper = new BudgetDtoMapper();

    @Mock
    private BudgetDao budgetDao;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    private Budget budget;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        budgetService = new BudgetServiceImpl(budgetDao, budgetDtoMapper);
        budget = budgetDtoMapper.unmapRow(budgetDto);
    }

    @Test
    public void testSetBudget() {
        doReturn(budget).when(budgetDao).createOrUpdate(budget);

        BudgetDto result = budgetService.set(budgetDto);

        assertEquals(budgetDtoMapper.mapRow(budget), result);
    }

    @Test
    public void testGetBudget() {
        doReturn(budget).when(budgetDao).get();

        BudgetDto result = budgetService.get();

        assertEquals(budgetDtoMapper.mapRow(budget), result);
    }
}