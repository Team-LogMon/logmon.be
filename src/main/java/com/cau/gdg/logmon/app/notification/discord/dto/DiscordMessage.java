package com.cau.gdg.logmon.app.notification.discord.dto;

import com.cau.gdg.logmon.app.notification.dto.LogAlertDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.cau.gdg.logmon.app.shared.TimeStampUtil.formatTimestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscordMessage {
    @JsonProperty("username")
    private String username;
    @JsonProperty("avatar_url")
    private String avatarUrl; // 아바타 url
    @JsonProperty("embeds")
    private List<Embed> embeds; // 안에들어갈 내용

    public static DiscordMessage of(LogAlertDto logAlertDto) {
        return DiscordMessage.builder()
                .username("LogMon")
                .avatarUrl("https://firebasestorage.googleapis.com/v0/b/logmon-4ba86.firebasestorage.app/o/app%2Flogo.png?alt=media&token=3510f29a-ef6e-4ec0-b0ce-c539b47f1130")
                .embeds(List.of(Embed.of(logAlertDto)))
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Embed {
        private String title;
        private int color;
        private String url;
        private List<Field> fields;
        private Footer footer;

        public static Embed of(LogAlertDto logAlertDto) {
            return new DiscordMessage.Embed(
                    "LogMon Alert",
                    logAlertDto.getLogSeverity().getColor(),
                    "https://logmon-4ba86.web.app/app/" + logAlertDto.getProjectId() +"/dashboard",
                    List.of(
                            new DiscordMessage.Field("프로젝트 제목", logAlertDto.getProjectTitle(), false),
                            new DiscordMessage.Field("심각도", logAlertDto.getLogSeverity().getLabel(), false),
                            new DiscordMessage.Field("내용", logAlertDto.getLogMessage(), false),
                            new DiscordMessage.Field("발생 시간", formatTimestamp(logAlertDto.getTimeStamp()), false)
                    ),
                    new DiscordMessage.Footer("자세한 로그 세부 사항은 제목을 클릭해주십시오.")
            );
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Field {
        private String name;
        private String value;
        private boolean inline;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Footer {
        private String text;
    }
}
