package com.github.epoddubny.testtaskrbkmoney.service.mq;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true")
@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topic) {
        log.info("Send message: {} to topic: {}", message, topic);
        kafkaTemplate.send(topic, message);
    }
}
