package com.smartcity.mapperDto;

import com.smartcity.domain.Budget;
import com.smartcity.dto.BudgetDto;
import org.springframework.stereotype.Component;

@Component
public class BudgetDtoMapper {

    public BudgetDto mapRow(Budget budget) {
        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setValue(budget.getValue());
        return budgetDto;
    }

    public Budget unmapRow(BudgetDto budgetDto) {
        Budget budget = new Budget();
        budget.setValue(budgetDto.getValue());
        return budget;
    }
}
