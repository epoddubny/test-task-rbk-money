package com.github.epoddubny.testtaskrbkmoney.service;

import com.github.epoddubny.testtaskrbkmoney.BaseSpringBootTest;
import com.github.epoddubny.testtaskrbkmoney.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TransactionServiceTest extends BaseSpringBootTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    public void findByIdShouldFindTransaction() {
        // Given
        Long id = 123l;
        Transaction expected = Transaction.builder()
                .id(id)
                .amount(new BigDecimal("100.05"))
                .data("{\"a\":1,\"b\":2}")
                .build();

        // When
        Optional<Transaction> transaction = transactionService.findById(id);

        // Then
        assertThat(transaction.isPresent()).isTrue();
        assertThat(transaction.get()).isEqualTo(expected);
    }

    @Test
    public void findByIdShouldNotFindTransaction() {
        // Given
        Long id = Long.MAX_VALUE;

        // When
        Optional<Transaction> transaction = transactionService.findById(id);

        // Then
        assertThat(transaction.isEmpty()).isTrue();
    }
}