package com.github.epoddubny.testtaskrbkmoney.service.mq;

import com.github.epoddubny.testtaskrbkmoney.avro.Transaction;
import com.github.epoddubny.testtaskrbkmoney.service.TransactionValidationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true")
@Service
@Slf4j
public class KafkaConsumer {
    private String transactionsTopicName;
    private String reportsTopicName;
    private TransactionValidationService transactionValidationService;

    public KafkaConsumer(
            @Value("${kafka.topics.transactions.name}") String transactionsTopicName,
            @Value("${kafka.topics.reports.name}") String reportsTopicName,
            TransactionValidationService transactionValidationService) {
        this.transactionsTopicName = transactionsTopicName;
        this.reportsTopicName = reportsTopicName;
        this.transactionValidationService = transactionValidationService;
    }

    @KafkaListener(topics = "${kafka.topics.transactions.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void transactionListener(ConsumerRecord<String, Transaction> record) {
        Transaction transaction = record.value();
        log.info("Receive transaction: {}, topic: {}", transaction, transactionsTopicName);
        transactionValidationService
                .validateTransaction(transaction.getPID(), BigDecimal.valueOf(transaction.getPAMOUNT()));
    }

    @KafkaListener(topics = "${kafka.topics.reports.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void reportListener(String message) {
        log.info("Receive report: {}, topic: {}", message, reportsTopicName);
    }
}
