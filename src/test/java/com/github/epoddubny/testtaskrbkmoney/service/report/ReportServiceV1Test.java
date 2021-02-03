package com.github.epoddubny.testtaskrbkmoney.service.report;

import com.github.epoddubny.testtaskrbkmoney.avro.ReportV1;
import com.github.epoddubny.testtaskrbkmoney.avro.ValidationResult;
import com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult;
import org.apache.avro.specific.SpecificRecord;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ReportServiceV1Test {
    private ReportService reportService = new ReportServiceV1();

    @Test
    public void shouldCreateReport() {
        assertReport(TransactionValidationResult.TRANSACTION_EQUALS, ValidationResult.TRANSACTION_EQUALS);
        assertReport(TransactionValidationResult.TRANSACTION_NOT_FOUND, ValidationResult.TRANSACTION_NOT_FOUND);
        assertReport(TransactionValidationResult.TRANSACTION_DIFFERENT, ValidationResult.TRANSACTION_DIFFERENT);
    }

    private void assertReport(TransactionValidationResult given, ValidationResult expected) {
        Long transactionId = 123l;

        SpecificRecord actual = reportService.createReport(transactionId, given).getAvroReport();

        assertThat(actual).isEqualTo(new ReportV1(transactionId, expected));
    }
}