package com.cau.gdg.logmon.app.notification;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription.NotificationPlatForm;
import com.cau.gdg.logmon.app.logs.Log;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification {

    @DocumentId
    private String id; //firebaseId;
    private String projectId; // 프로젝트 Id
    private NotificationPlatForm platForm; // 전송할 로그
    private String webhookUrl; // 웹훅 url
    private String content; // 내용
    private boolean isSend;
    @Builder.Default
    private Long createdAt = System.currentTimeMillis();; // 생성 시간

    public static Notification of(
            Log logs,
            LogAlertSubscription subscription,
            boolean isSend
    ){
        return Notification.builder()
                .projectId(logs.getProjectId())
                .platForm(subscription.getPlatform())
                .webhookUrl(subscription.getUrl())
                .content(logs.getMessage())
                .isSend(isSend)
                .build();
    }
}
