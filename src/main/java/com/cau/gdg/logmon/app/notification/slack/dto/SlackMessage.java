package com.cau.gdg.logmon.app.notification.slack.dto;

import com.cau.gdg.logmon.app.notification.dto.LogAlertDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.cau.gdg.logmon.app.notification.TimeStampUtil.formatTimestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlackMessage {
    private String username;
    @JsonProperty("icon_url")
    private String iconUrl;
    private List<Attachment> attachments;
    private List<Action> actions; // ✅ 추가

    public static SlackMessage of(LogAlertDto logAlertDto){
        return SlackMessage.builder()
                .username("LogMon")
                .iconUrl("https://yourdomain.com/logo.png")
                .attachments(List.of(
                        SlackMessage.Attachment.builder()
                                .color(String.valueOf(logAlertDto.getLogSeverity().getColor())) // 🔥 심각도 색상
                                .title("🚨 LogMon Alert")
                                .fields(List.of(
                                        new Field("프로젝트 제목", logAlertDto.getProjectTitle(), true),
                                        new Field("심각도", logAlertDto.getLogSeverity().getLabel(), true),
                                        new Field("내용", logAlertDto.getLogMessage(), false),
                                        new Field("발생 시간", formatTimestamp(logAlertDto.getTimeStamp()), false)
                                ))
                                .footer("자세한 로그 세부 사항은 제목을 클릭해주십시오.")
                                .build()
                ))
                .actions(List.of(
                        new Action(
                                "button",
                                "🔗 대시보드 열기",
                                "https://logmon-4ba86.web.app/app/" + logAlertDto.getProjectId() + "/dashboard"
                        )
                ))
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attachment {
        private String color;
        private String title;
        private List<Field> fields;
        private String footer;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Field {
        private String title;
        private String value;
        private boolean shortField;

        @JsonProperty("short")
        public boolean isShortField() {
            return shortField;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Action {
        private String type; // 항상 "button"
        private String text;
        private String url;
    }
}
