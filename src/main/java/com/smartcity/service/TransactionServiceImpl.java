package com.smartcity.service;

import com.smartcity.dao.TransactionDao;
import com.smartcity.dto.TransactionDto;
import com.smartcity.mapperDto.TransactionDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionDao transDao;
    private TransactionDtoMapper mapper;

    @Autowired
    public TransactionServiceImpl(TransactionDao transDao, TransactionDtoMapper mapper) {
        this.transDao = transDao;
        this.mapper = mapper;
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        return mapper.
                transactionToTransactionDto(transDao.
                        create(mapper.transactionDtoToTransaction(transactionDto)));
    }

    @Override
    public TransactionDto findById(Long id) {
        return mapper.transactionToTransactionDto(transDao.findById(id));
    }

    @Override
    public TransactionDto update(TransactionDto transactionDto) {
        return mapper.
                transactionToTransactionDto(
                        transDao.update(mapper.transactionDtoToTransaction(transactionDto)));
    }

    @Override
    public boolean delete(Long id) {
        return transDao.delete(id);
    }

    @Override
    public List<TransactionDto> findByTaskId(Long id) {
        return transDao.findByTaskId(id).stream()
                .map(t -> mapper.transactionToTransactionDto(t)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findByDate(Long id, LocalDateTime from, LocalDateTime to) {
        return transDao.findByDate(id,from,to).stream()
                .map(t -> mapper.transactionToTransactionDto(t)).collect(Collectors.toList());
    }

}
