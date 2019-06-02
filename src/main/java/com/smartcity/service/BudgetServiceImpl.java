package com.smartcity.service;

import com.smartcity.dao.BudgetDao;
import com.smartcity.dto.BudgetDto;
import com.smartcity.mapperDto.BudgetDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {

    private BudgetDao budgetDao;
    private BudgetDtoMapper budgetDtoMapper;

    @Autowired
    public BudgetServiceImpl(BudgetDao budgetDao, BudgetDtoMapper budgetDtoMapper) {
        this.budgetDao = budgetDao;
        this.budgetDtoMapper = budgetDtoMapper;
    }

    @Override
    public BudgetDto get() {
        return budgetDtoMapper.mapRow(budgetDao.get());
    }

    @Override
    public BudgetDto set(BudgetDto budget) {
        return budgetDtoMapper.mapRow(budgetDao.createOrUpdate(budgetDtoMapper.unmapRow(budget)));
    }
}
