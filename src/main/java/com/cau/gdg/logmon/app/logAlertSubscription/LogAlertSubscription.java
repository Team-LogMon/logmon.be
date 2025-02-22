package com.cau.gdg.logmon.app.logAlertSubscription;

import com.cau.gdg.logmon.app.shared.LogSeverity;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import lombok.Getter;

@Getter
public class LogAlertSubscription {
    @DocumentId
    private String id;

    private String name;
    private String projectId;
    /**
     * webhook platform
     */
    private String platform;
    /**
     * webhook url
     */
    private String url;
    private LogSeverity alertThreshold;

    /**
     * 일일 한도
     */
    private int dailyQuotaLimit;
    private int dailyQuotaUsed;

    /**
     * 월간 한도
     */
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

    @Exclude
    public boolean isDailyQuotaExceed() {
        return dailyQuotaUsed >= dailyQuotaLimit;
    }

    @Exclude
    public boolean isMonthlyQuotaExceed() {
        return monthlyQuotaUsed >= monthlyQuotaLimit;
    }

    @Exclude
    public boolean isQuotaExceed() {
        return isDailyQuotaExceed() || isMonthlyQuotaExceed();
    }

    public void increaseUsedCount() {
        this.dailyQuotaUsed++;
        this.monthlyQuotaUsed++;
        this.updatedAt = System.currentTimeMillis();
    }
}
