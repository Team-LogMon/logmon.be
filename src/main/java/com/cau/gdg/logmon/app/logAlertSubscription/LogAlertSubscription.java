package com.cau.gdg.logmon.app.logAlertSubscription;

import com.cau.gdg.logmon.app.shared.LogSeverity;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;

@Getter
public class LogAlertSubscription {
    @DocumentId
    private String id;

    private String name;
    private String projectId;
    private String platform;
    private String url;
    private LogSeverity alertThreshold;

    private int dailyQuotaLimit;
    private int dailyQuotaUsed;

    private int monthlyQuotaLimit;
    private int monthlyQuotaUsed;

    private long createdAt;
    private long updatedAt;

    public static LogAlertSubscription of(
            String projectId,
            String name,
            String platform,
            String url,
            LogSeverity alertThreshold
    ) {
        long now = System.currentTimeMillis();

        LogAlertSubscription subscription = new LogAlertSubscription();
        subscription.projectId = projectId;
        subscription.name = name;
        subscription.platform = platform;
        subscription.url = url;
        subscription.alertThreshold = alertThreshold;
        subscription.dailyQuotaLimit = 100;
        subscription.dailyQuotaUsed = 0;
        subscription.monthlyQuotaLimit = 1500;
        subscription.monthlyQuotaUsed = 0;
        subscription.createdAt = now;
        subscription.updatedAt = now;

        return subscription;
    }
}
