package com.cau.gdg.logmon.app.logAlertSubscription;

import com.cau.gdg.logmon.app.logAlertSubscription.dto.LogAlertSubscriptionCreateRequest;
import com.cau.gdg.logmon.app.shared.LogSeverity;

import java.util.List;

public interface LogAlertSubscriptionService {
    List<LogAlertSubscription> findByProjectId(String projectId);

    void createLogAlertSubscription(LogAlertSubscriptionCreateRequest request);

    void deleteLogAlertSubscription(String id);

    /**
     * 알림을 전송해야할 구독 목록 찾기
     * @param projectId 프로젝트 ID
     * @param alertThreshold 심각도
     */
    List<LogAlertSubscription> getNotifiableSubscription(String projectId, LogSeverity alertThreshold);
}
