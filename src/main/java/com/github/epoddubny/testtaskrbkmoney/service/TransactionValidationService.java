package com.github.epoddubny.testtaskrbkmoney.service;

import com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult;
import com.github.epoddubny.testtaskrbkmoney.model.Report;
import com.github.epoddubny.testtaskrbkmoney.model.Transaction;
import com.github.epoddubny.testtaskrbkmoney.service.notification.NotificationService;
import com.github.epoddubny.testtaskrbkmoney.service.report.ReportService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult.TRANSACTION_DIFFERENT;
import static com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult.TRANSACTION_EQUALS;
import static com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult.TRANSACTION_NOT_FOUND;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionValidationService {
    private TransactionService transactionService;
    private List<ReportService> reportServices;
    private List<NotificationService> notificationServices;

    public void validateTransactionAndSendReport(@NonNull Long transactionId, @NonNull BigDecimal amount) {
        log.info("Validate transaction id: {}, amount: {}", transactionId, amount);
        Optional<Transaction> transaction = transactionService.findById(transactionId);
        TransactionValidationResult validationResult = TRANSACTION_NOT_FOUND;
        if (transaction.isPresent()) {
            validationResult = compareTransactionAmount(amount, transaction.get().getAmount());
        }
        log.info("Transaction validation result id: {}, result: {}", transactionId, validationResult);
        createReportAndSendNotification(transactionId, validationResult);
    }

    private TransactionValidationResult compareTransactionAmount(BigDecimal receivedAmount, BigDecimal currentAmount) {
        int compareResult = receivedAmount.compareTo(currentAmount);
        if (compareResult == 0) {
            return TRANSACTION_EQUALS;
        } else {
            return TRANSACTION_DIFFERENT;
        }
    }

    private void createReportAndSendNotification(Long transactionId, TransactionValidationResult validationResult) {
        for (ReportService reportService : reportServices) {
            Report report = reportService.createReport(transactionId, validationResult);
            sendNotification(report);
        }
    }

    private void sendNotification(Report report) {
        for (NotificationService notificationService : notificationServices) {
            notificationService.sendReportNotification(report);
        }
    }
}
