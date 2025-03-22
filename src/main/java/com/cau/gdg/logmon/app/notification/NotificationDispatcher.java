package com.cau.gdg.logmon.app.notification;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.notification.dto.LogAlertDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationDispatcher {

    private final List<NotificationSender> strategies;

    /**
     * Dto를 갈아끼우는 역할을 담당함.
     * @param strategies 구현체들
     */
    public NotificationDispatcher(List<NotificationSender> strategies) {
        this.strategies = strategies;
    }

    /**
     * 어떤 dto로 전송할지 담당하는 역할
     * @param platForm 로그 플랫폼
     * @param dto 전송할 dto
     */
    public void sendToPlatform(
            LogAlertSubscription.NotificationPlatForm platForm,
            LogAlertDto dto
    ) {
        strategies.stream()
                .filter(s -> s.supports(platForm))
                .findFirst()
                .ifPresent(s -> s.send(dto));
    }
}
