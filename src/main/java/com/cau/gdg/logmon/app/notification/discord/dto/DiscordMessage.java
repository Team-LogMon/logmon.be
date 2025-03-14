package com.cau.gdg.logmon.app.notification.discord.dto;

import com.cau.gdg.logmon.app.logs.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscordMessage {
    private String content;
    private List<Embed> embeds;

    public static DiscordMessage of(Log logs, String projectName) {
        return DiscordMessage.builder()
                .embeds(List.of(Embed.of(logs, projectName)))
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Embed {
        private String title;
        private int color;
        private String url;
        private List<Field> fields;
        private Footer footer;

        public static Embed of(
                Log logs,
                String projectTitle
        ) {
            return new DiscordMessage.Embed(
                    "LogMon Alert",
                    logs.getSeverity().getColor(),
                    "https://logmon-4ba86.web.app/app/" + logs.getProjectId() +"/dashboard",
                    List.of(
                            new DiscordMessage.Field("프로젝트 제목", projectTitle, false),
                            new DiscordMessage.Field("심각도", logs.getSeverity().getLabel(), false),
                            new DiscordMessage.Field("내용", logs.getMessage(), false),
                            new DiscordMessage.Field("발생 시간", formatTimestamp(logs.getTimeStamp()), false)
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
    public static class Thumbnail {
        private String url; // image Logo
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Footer {
        private String text;
    }

    public static String formatTimestamp(Long timestamp) {
        // ✅ 1️⃣ Unix Timestamp(초 단위)를 Instant로 변환
        Instant instant = Instant.ofEpochSecond(timestamp);

        // ✅ 2️⃣ 한국 시간(KST)으로 변환
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));

        // ✅ 3️⃣ 원하는 형식으로 포맷 (예: "YYYY-MM-DD HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
