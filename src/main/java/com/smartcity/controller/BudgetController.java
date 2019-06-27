package com.smartcity.controller;

import com.smartcity.dto.BudgetDto;
import com.smartcity.service.BudgetService;
import com.smartcity.service.BudgetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    private BudgetService service;

    @Autowired
    public BudgetController(BudgetServiceImpl service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BudgetDto get() {
        return service.get();
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getBudgetControllerPutAllowedRoles())")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BudgetDto put(@RequestBody BudgetDto budgetDto) {
        return service.set(budgetDto);
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getBudgetControllerPutAllowedRoles())")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
            value = "/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BudgetDto deposit(@RequestBody Long amount) {
        return service.deposit(amount);
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getBudgetControllerPutAllowedRoles())")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
            value = "/withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BudgetDto withdraw(@RequestBody Long amount) {
        return service.withdraw(amount);
    }
}