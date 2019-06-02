package com.smartcity.controller;

import com.smartcity.dto.BudgetDto;
import com.smartcity.service.BudgetService;
import com.smartcity.service.BudgetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    private BudgetService service;

    @Autowired
    public BudgetController(BudgetServiceImpl service) {
        this.service = service;
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public BudgetDto get() {
        return service.get();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BudgetDto put(@RequestBody BudgetDto budgetDto) {
        return service.set(budgetDto);
    }
}