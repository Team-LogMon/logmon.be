package com.cau.gdg.logmon.app.notification.listener;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscriptionService;
import com.cau.gdg.logmon.app.logs.Log;
import com.cau.gdg.logmon.app.logs.LogCreatedEvent;
import com.cau.gdg.logmon.app.notification.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogCreateEventListener {

    private final NotificationSender notificationSender;
    private final LogAlertSubscriptionService logAlertSubscriptionService;

    @EventListener
    public void LogCreateEvent(LogCreatedEvent event){
        Log logs = event.log();
        List<LogAlertSubscription> notifiableSubscription = logAlertSubscriptionService.getNotifiableSubscription(logs.getProjectId(), logs.getSeverity());
        for (LogAlertSubscription logAlertSubscription : notifiableSubscription) {
            notificationSender.send(logs, logAlertSubscription);
        }

    }
}
