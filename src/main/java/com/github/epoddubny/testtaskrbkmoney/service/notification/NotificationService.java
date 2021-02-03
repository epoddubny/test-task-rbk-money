package com.github.epoddubny.testtaskrbkmoney.service.notification;

import com.github.epoddubny.testtaskrbkmoney.model.Report;

public interface NotificationService {
    void sendReportNotification(Report report);
}
