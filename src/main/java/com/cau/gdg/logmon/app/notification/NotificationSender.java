package com.cau.gdg.logmon.app.notification;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription.NotificationPlatForm;
import com.cau.gdg.logmon.app.notification.dto.LogAlertDto;

public interface NotificationSender {
    boolean supports(NotificationPlatForm platform);

    void send(LogAlertDto logAlertDto);
    // 전략 패턴 또는 팩토리 패턴 적용하기

}
