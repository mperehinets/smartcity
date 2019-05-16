package com.smartcity.mapperDto;

import com.smartcity.domain.Budget;
import com.smartcity.dto.BudgetDto;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BudgetDtoMapperTest {

    private Budget budget;
    private BudgetDto budgetDto;

    private final BudgetDtoMapper mapper = new BudgetDtoMapper();

    @BeforeEach
    public void init() {
        budget = new Budget(10L);
        budgetDto = new BudgetDto(10L);
    }

    @Test
    public void testConvertDaoToDto() {
        assertEquals(budgetDto, mapper.mapRow(budget));
    }

    @Test
    public void testConvertDtoToDao() {
        assertEquals(budget, mapper.unmapRow(budgetDto));
    }

}