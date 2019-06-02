package com.smartcity.dto;

import java.util.Objects;

public class BudgetDto {

    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public BudgetDto(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetDto budgetDto = (BudgetDto) o;
        return value.equals(budgetDto.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public BudgetDto() {

    }
}
