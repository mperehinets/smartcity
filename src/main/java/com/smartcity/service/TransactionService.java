package com.smartcity.service;

import com.smartcity.dto.TransactionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionDto create(TransactionDto transactionDto);

    TransactionDto findById(Long id);

    TransactionDto update(TransactionDto transactionDto);

    boolean delete(Long id);

    List<TransactionDto> findByTaskId(Long id);

    List<TransactionDto> findByDate(Long id, LocalDateTime from, LocalDateTime to);

}
