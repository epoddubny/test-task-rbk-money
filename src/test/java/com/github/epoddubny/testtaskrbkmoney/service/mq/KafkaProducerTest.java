package com.github.epoddubny.testtaskrbkmoney.service.mq;

import com.github.epoddubny.testtaskrbkmoney.BaseSpringBootTest;
import com.github.epoddubny.testtaskrbkmoney.avro.ReportV1;
import com.github.epoddubny.testtaskrbkmoney.avro.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.github.epoddubny.testtaskrbkmoney.avro.ValidationResult.TRANSACTION_DIFFERENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;


class KafkaProducerTest extends BaseSpringBootTest  {
    @SpyBean
    private KafkaConsumer kafkaConsumerSpy;
    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void sendReportShouldSendReport() {
        // Given
        ReportV1 report = new ReportV1(123l, TRANSACTION_DIFFERENT);

        // When
        kafkaProducer.sendReport(report);

        // Then
        verify(kafkaConsumerSpy, timeout(5000).times(1))
                .reportListener(any());
    }

    @Test
    public void sendReportShouldNotSendReport() throws Exception {
        // Given
        Transaction transaction = new Transaction();

        // When
        kafkaProducer.sendReport(transaction);

        // Then
        Thread.sleep(5000);
        verifyNoInteractions(kafkaConsumerSpy);
    }
}