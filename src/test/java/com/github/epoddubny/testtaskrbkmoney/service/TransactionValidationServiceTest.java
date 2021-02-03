package com.github.epoddubny.testtaskrbkmoney.service;

import com.github.epoddubny.testtaskrbkmoney.BaseSpringBootTest;
import com.github.epoddubny.testtaskrbkmoney.model.Report;
import com.github.epoddubny.testtaskrbkmoney.model.Transaction;
import com.github.epoddubny.testtaskrbkmoney.service.notification.KafkaNotificationService;
import com.github.epoddubny.testtaskrbkmoney.service.notification.NotificationService;
import com.github.epoddubny.testtaskrbkmoney.service.report.ReportService;
import com.github.epoddubny.testtaskrbkmoney.service.report.ReportServiceV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult.TRANSACTION_DIFFERENT;
import static com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult.TRANSACTION_EQUALS;
import static com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult.TRANSACTION_NOT_FOUND;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionValidationServiceTest extends BaseSpringBootTest {
    @MockBean
    private TransactionService transactionServiceMock;
    @MockBean(ReportServiceV1.class)
    private ReportService reportServiceMock;
    @MockBean(KafkaNotificationService.class)
    private NotificationService notificationServiceMock;

    @Autowired
    private TransactionValidationService transactionValidationService;

    @Test
    public void validateTransactionShouldSendNotFoundReport() {
        // Given
        Long transactionId = 123l;
        BigDecimal amount = BigDecimal.TEN;
        Report actualReport = new ReportServiceV1.ReportFormatV1(transactionId, TRANSACTION_NOT_FOUND);

        when(transactionServiceMock.findById(transactionId)).thenReturn(Optional.empty());
        when(reportServiceMock.createReport(transactionId, TRANSACTION_NOT_FOUND)).thenReturn(actualReport);

        // When
        transactionValidationService.validateTransactionAndSendReport(transactionId, amount);

        // Then
        verify(notificationServiceMock).sendReportNotification(actualReport);
    }

    @Test
    public void validateTransactionShouldSendEqualsReport() {
        // Given
        Long transactionId = 123l;
        BigDecimal amount = BigDecimal.TEN;
        Transaction transaction = Transaction.builder()
                .id(transactionId)
                .amount(amount).build();
        Report actualReport = new ReportServiceV1.ReportFormatV1(transactionId, TRANSACTION_EQUALS);

        when(transactionServiceMock.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(reportServiceMock.createReport(transactionId, TRANSACTION_EQUALS)).thenReturn(actualReport);

        // When
        transactionValidationService.validateTransactionAndSendReport(transactionId, amount);

        // Then
        verify(notificationServiceMock).sendReportNotification(actualReport);
    }

    @Test
    public void validateTransactionShouldSendDifferentReport() {
        // Given
        Long transactionId = 123l;
        BigDecimal amount = BigDecimal.TEN;
        Transaction transaction = Transaction.builder()
                .id(transactionId)
                .amount(BigDecimal.ONE).build();
        Report actualReport = new ReportServiceV1.ReportFormatV1(transactionId, TRANSACTION_DIFFERENT);

        when(transactionServiceMock.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(reportServiceMock.createReport(transactionId, TRANSACTION_DIFFERENT)).thenReturn(actualReport);

        // When
        transactionValidationService.validateTransactionAndSendReport(transactionId, amount);

        // Then
        verify(notificationServiceMock).sendReportNotification(actualReport);
    }
}