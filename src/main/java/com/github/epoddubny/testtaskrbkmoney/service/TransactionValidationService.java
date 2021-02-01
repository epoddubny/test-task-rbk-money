package com.github.epoddubny.testtaskrbkmoney.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class TransactionValidationService {
    public void validateTransaction(Long id, BigDecimal amount) {
        // todo
    }
}
