package com.cau.gdg.logmon.app.notification.listener;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscriptionService;
import com.cau.gdg.logmon.app.logs.Log;
import com.cau.gdg.logmon.app.notification.NotificationDispatcher;
import com.cau.gdg.logmon.app.notification.dto.LogAlertDto;
import com.cau.gdg.logmon.app.notification.event.LogCreatedNotificationEvent;
import com.cau.gdg.logmon.app.project.Project;
import com.cau.gdg.logmon.app.project.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogCreatedNotificationListener {

    private final NotificationDispatcher notificationDispatcher;
    private final LogAlertSubscriptionService logAlertSubscriptionService;
    private final ProjectService projectService;

    @EventListener
    public void logCreatedNotification(LogCreatedNotificationEvent event){
        Log logs = event.log();
        List<LogAlertSubscription> notifiableSubscription = logAlertSubscriptionService.getNotifiableSubscription(logs.getProjectId(), logs.getSeverity());

        // 로그 전송 Dto 생성
        Project project = projectService.getProject(logs.getProjectId());
        // 플랫폼별 로그 전송
        for (LogAlertSubscription logAlertSubscription : notifiableSubscription) {
            LogAlertDto logAlertDto = LogAlertDto.of(
                    logs,
                    logAlertSubscription,
                    project
            );

            notificationDispatcher.sendToPlatform(logAlertSubscription.getPlatform(), logAlertDto);
        }

    }
}
