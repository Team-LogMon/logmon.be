package com.cau.gdg.logmon.app.notification.dto;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.logs.Log;
import com.cau.gdg.logmon.app.project.Project;
import com.cau.gdg.logmon.app.shared.LogSeverity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogAlertDto {
    private String projectId; // 프로젝트 Id
    private String projectTitle; // 프로젝트 제목
    private LogSeverity logSeverity; // 로그 심각도
    private String logMessage; // 로그 메세지
    private LogAlertSubscription.NotificationPlatForm platForm; // 전송할 로그
    private String webHookUrl; // 웹훅 url
    private long timeStamp; // 로그 생성 시간

    public static LogAlertDto of(
            Log log,
            LogAlertSubscription logAlertSubscription,
            Project project){
        return LogAlertDto.builder()
                .projectId(project.getId())
                .projectTitle(project.getTitle())
                .logSeverity(log.getSeverity())
                .logMessage(log.getMessage())
                .platForm(logAlertSubscription.getPlatform())
                .webHookUrl(logAlertSubscription.getUrl())
                .timeStamp(log.getTimeStamp())
                .build();
    }
}
