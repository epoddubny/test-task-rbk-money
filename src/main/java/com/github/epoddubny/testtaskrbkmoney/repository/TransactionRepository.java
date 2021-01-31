package com.github.epoddubny.testtaskrbkmoney.repository;

import com.github.epoddubny.testtaskrbkmoney.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
