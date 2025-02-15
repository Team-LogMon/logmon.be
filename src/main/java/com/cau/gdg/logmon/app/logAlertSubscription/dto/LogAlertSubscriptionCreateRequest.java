package com.cau.gdg.logmon.app.logAlertSubscription.dto;

import com.cau.gdg.logmon.app.shared.LogSeverity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LogAlertSubscriptionCreateRequest {
    private String name;
    private LogSeverity alertThreshold;
    private String platform;
    private String url;
    private String projectId;
}
