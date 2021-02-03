package com.github.epoddubny.testtaskrbkmoney.service.notification;

import com.github.epoddubny.testtaskrbkmoney.model.Report;
import com.github.epoddubny.testtaskrbkmoney.service.mq.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true")
@Service
@Slf4j
@AllArgsConstructor
public class KafkaNotificationService implements NotificationService {
    private KafkaProducer kafkaProducer;

    @Override
    public void sendReportNotification(Report report) {
        log.info("Send report: {}", report);
        kafkaProducer.sendReport(report.getAvroReport());
    }
}
