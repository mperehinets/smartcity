package com.smartcity.controller;

import com.smartcity.dto.TransactionDto;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;
import com.smartcity.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transService;

    @Autowired
    public TransactionController(TransactionService transService) {
        this.transService = transService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionDto findById(@PathVariable("id") Long id) {
        return transService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionDto updateTransaction(
            @Validated(ExistingRecord.class)
            @PathVariable("id") Long id,
            @RequestBody TransactionDto transactionDto) {
        transactionDto.setId(id);
        return transService.update(transactionDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public boolean deleteTransaction(@PathVariable("id") Long id) {
        return transService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/taskId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDto> findByTaskId(@PathVariable("id") Long taskId) {
        return transService.findByTaskId(taskId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public TransactionDto createTransaction(@Validated(NewRecord.class) @RequestBody TransactionDto transactionDto) {
        return transService.create(transactionDto);
    }

}