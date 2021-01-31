package com.github.epoddubny.testtaskrbkmoney.service;

import com.github.epoddubny.testtaskrbkmoney.model.Transaction;
import com.github.epoddubny.testtaskrbkmoney.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private TransactionRepository transactionRepository;

    public Optional<Transaction> findById(Long id) {
        log.info("Find transaction by id: {}", id);
        Optional<Transaction> transaction = transactionRepository.findById(id);
        log.info("Result of getting transaction by id: {}, transaction: {}", id, transaction);
        return transaction;
    }
}
