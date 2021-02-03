package com.github.epoddubny.testtaskrbkmoney.service.report;

import com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult;
import com.github.epoddubny.testtaskrbkmoney.model.Report;
import lombok.NonNull;

public interface ReportService {
    Report createReport(@NonNull Long transactionId, @NonNull TransactionValidationResult transactionValidationResult);
}
