package com.smartcity.dto.constraint;

import com.smartcity.dto.TaskDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LessThanValidator implements ConstraintValidator<LessThan, TaskDto> {

    @Override
    public void initialize(LessThan constraintAnnotation) {

    }

    @Override
    public boolean isValid(TaskDto taskDto, ConstraintValidatorContext constraintValidatorContext) {
        Long budget = taskDto.getBudget();
        Long approvedBudget = taskDto.getApprovedBudget();

        if (budget == null || approvedBudget == null) {
            return true;
        }

        return approvedBudget <= budget;
    }

}
