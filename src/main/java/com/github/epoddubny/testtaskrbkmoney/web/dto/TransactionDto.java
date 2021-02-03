package com.github.epoddubny.testtaskrbkmoney.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long transactionId;
    private BigDecimal amount;
    private String data;
}
