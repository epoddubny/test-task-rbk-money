package com.github.epoddubny.testtaskrbkmoney.web;

import com.github.epoddubny.testtaskrbkmoney.avro.Transaction;
import com.github.epoddubny.testtaskrbkmoney.web.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {
    private String transactionsTopicName;
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public SendMessageController(
            @Value("${kafka.topics.transactions.name}") String transactionsTopicName,
            KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.transactionsTopicName = transactionsTopicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(value = "/send-transaction")
    public void sendTransactionMessage(@RequestBody TransactionDto request) {
        Transaction transaction = new Transaction(request.getTransactionId(), request.getAmount().doubleValue(), request.getData());
        kafkaTemplate.send(transactionsTopicName, transaction.getPID().toString(), transaction);
    }
}
