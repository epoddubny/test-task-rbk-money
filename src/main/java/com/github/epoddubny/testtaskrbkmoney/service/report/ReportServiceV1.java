package com.github.epoddubny.testtaskrbkmoney.service.report;

import com.github.epoddubny.testtaskrbkmoney.avro.ReportV1;
import com.github.epoddubny.testtaskrbkmoney.avro.ValidationResult;
import com.github.epoddubny.testtaskrbkmoney.enums.TransactionValidationResult;
import com.github.epoddubny.testtaskrbkmoney.model.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "report.v1", name = "enabled", havingValue = "true")
public class ReportServiceV1 implements ReportService {

    @Override
    public Report createReport(Long transactionId, TransactionValidationResult transactionValidationResult) {
        return new ReportFormatV1(transactionId, transactionValidationResult);
    }

    @Data
    @AllArgsConstructor
    public static class ReportFormatV1 implements Report {
        private Long transactionId;
        private TransactionValidationResult transactionValidationResult;

        @Override
        public SpecificRecord getAvroReport() {
            return new ReportV1(transactionId, getValidationResult(transactionValidationResult));
        }

        private ValidationResult getValidationResult(TransactionValidationResult validationResult) {
            ValidationResult result = null;
            switch(validationResult) {
                case TRANSACTION_EQUALS:
                    result = ValidationResult.TRANSACTION_EQUALS;
                    break;
                case TRANSACTION_DIFFERENT:
                    result = ValidationResult.TRANSACTION_DIFFERENT;
                    break;
                case TRANSACTION_NOT_FOUND:
                    result = ValidationResult.TRANSACTION_NOT_FOUND;
                    break;
            };
            return result;
        }
    }
}
