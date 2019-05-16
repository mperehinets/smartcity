package com.smartcity.service;

import com.smartcity.dto.BudgetDto;
import org.springframework.stereotype.Service;

@Service
public interface BudgetService {

    BudgetDto get();

    BudgetDto set(BudgetDto budget);

}
