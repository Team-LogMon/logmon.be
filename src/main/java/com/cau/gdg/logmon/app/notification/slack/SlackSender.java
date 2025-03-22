package com.cau.gdg.logmon.app.notification.slack;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.notification.Notification;
import com.cau.gdg.logmon.app.notification.NotificationRepository;
import com.cau.gdg.logmon.app.notification.NotificationSender;
import com.cau.gdg.logmon.app.notification.dto.LogAlertDto;
import com.cau.gdg.logmon.app.notification.slack.dto.SlackMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class SlackSender implements NotificationSender {

    private final NotificationRepository notificationRepository;

    @Override
    public boolean supports(LogAlertSubscription.NotificationPlatForm platform) {
        return platform.equals(LogAlertSubscription.NotificationPlatForm.SLACK);
    }

    @Override
    public void send(LogAlertDto logAlertDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON.toString());

        // Dto 를 json 형식으로 메세지 만들 예정
        ObjectMapper mapper = new ObjectMapper();
        SlackMessage slackMessage = SlackMessage.of(logAlertDto);

        RestTemplate template = new RestTemplate();

        try {
            String json = mapper.writeValueAsString(slackMessage);
            HttpEntity<String> body = new HttpEntity<>(json, httpHeaders);

            ResponseEntity<String> response = template.exchange(
                    logAlertDto.getWebHookUrl(),
                    HttpMethod.POST,
                    body,
                    String.class
            );

            boolean isSend = true;
            if (response.getStatusCode().value() != HttpStatus.NO_CONTENT.value()) {
                isSend = false;
                log.error("메시지 전송 이후 에러 발생");
            }

            Notification notification = Notification.of(logAlertDto, isSend);
            notificationRepository.save(notification);

        } catch (Exception e) {
            log.error("에러 발생 :: " + e);
        }
    }
}
