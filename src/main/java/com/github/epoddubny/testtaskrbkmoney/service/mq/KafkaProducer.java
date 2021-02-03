package com.github.epoddubny.testtaskrbkmoney.service.mq;

import com.github.epoddubny.testtaskrbkmoney.avro.ReportV1;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true")
@Service
@Slf4j
public class KafkaProducer {
    @Value("${kafka.topics.reports.name}")
    private String reportsTopicName;
    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(
            @Value("${kafka.topics.reports.name}") String reportsTopicName,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.reportsTopicName = reportsTopicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReport(SpecificRecord report) {
        if (report instanceof ReportV1) {
            log.info("Send report: {} to topic: {}", report, reportsTopicName);
            kafkaTemplate.send(reportsTopicName, report);
        } else {
            log.warn("Unknown report type");
        }
    }
}
