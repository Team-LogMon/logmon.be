package com.cau.gdg.logmon.app.notification.discord;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.logs.Log;
import com.cau.gdg.logmon.app.notification.Notification;
import com.cau.gdg.logmon.app.notification.NotificationRepository;
import com.cau.gdg.logmon.app.notification.NotificationSender;
import com.cau.gdg.logmon.app.notification.discord.dto.DiscordMessage;
import com.cau.gdg.logmon.app.project.Project;
import com.cau.gdg.logmon.app.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordSender implements NotificationSender {

    private final NotificationRepository notificationRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void send(
            Log logs,
            LogAlertSubscription subscription
    ) {
        if(!subscription.getPlatform().equals(LogAlertSubscription.NotificationPlatForm.DISCORD))
            return;

        Project project = projectRepository.findById(logs.getProjectId()).orElseThrow();

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON.toString());

            // Dto 를 json 형식으로 메세지 만들 예정
            DiscordMessage discordMessage = DiscordMessage.of(logs, project.getTitle());
            HttpEntity<DiscordMessage> message = new HttpEntity<>(discordMessage, httpHeaders);

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> response = template.exchange(
                    subscription.getUrl(),
                    HttpMethod.POST,
                    message,
                    String.class
            );

            boolean isSend = true;
            if(response.getStatusCode().value() != HttpStatus.NO_CONTENT.value()){
                isSend = false;
                log.error("메시지 전송 이후 에러 발생");
            }

            Notification notification = Notification.of(logs, subscription, isSend);
            notificationRepository.save(notification);

        } catch (Exception e) {
            log.error("에러 발생 :: " + e);
        }

    }
}
