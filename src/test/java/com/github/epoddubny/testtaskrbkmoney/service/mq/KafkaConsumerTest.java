package com.github.epoddubny.testtaskrbkmoney.service.mq;

import com.github.epoddubny.testtaskrbkmoney.BaseSpringBootTest;
import com.github.epoddubny.testtaskrbkmoney.avro.Transaction;
import com.github.epoddubny.testtaskrbkmoney.service.TransactionValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

class KafkaConsumerTest extends BaseSpringBootTest {
    @MockBean(TransactionValidationService.class)
    private TransactionValidationService transactionValidationServiceMock;
    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;
    @Value("${kafka.topics.transactions.name}")
    private String transactionsTopicName;

    @Test
    public void transactionListenerShouldReceiveTransaction() {
        // Given
        Transaction transaction = new Transaction(123l, 94.7, "20160101120000");

        // When
        kafkaTemplate.send(transactionsTopicName, transaction.getPID().toString(), transaction);

        // Then
        verify(transactionValidationServiceMock, timeout(5000).times(1))
                .validateTransactionAndSendReport(eq(transaction.getPID()), eq(BigDecimal.valueOf(transaction.getPAMOUNT())));
    }
}