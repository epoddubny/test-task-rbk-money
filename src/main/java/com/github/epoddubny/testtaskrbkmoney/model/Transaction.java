package com.github.epoddubny.testtaskrbkmoney.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder
@Table("transactions")
public class Transaction {
    @Id
    private Long id;
    private BigDecimal amount;
    private String data;
}
