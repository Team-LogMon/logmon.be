package com.cau.gdg.logmon.app.notification.discord.dto;

import com.cau.gdg.logmon.app.logs.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
                            new DiscordMessage.Field("Project ID", projectTitle, false),
                            new DiscordMessage.Field("Severity", logs.getSeverity().getLabel(), false),
                            new DiscordMessage.Field("Content", logs.getMessage(), false),
                            new DiscordMessage.Field("Timestamp", formatTimestamp(logs.getTimeStamp()), false)
                    ),
                    new DiscordMessage.Footer("Click the title if you want to see details.")
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
        ZonedDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault());

        // ✅ 3️⃣ 원하는 형식으로 포맷 (예: "YYYY-MM-DD HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String format = dateTime.format(formatter);
        return format;
    }
}
