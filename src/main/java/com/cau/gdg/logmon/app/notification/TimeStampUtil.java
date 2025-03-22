package com.cau.gdg.logmon.app.notification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeStampUtil {
    public static String formatTimestamp(Long timestamp) {
        // 밀리초로 변환
        Instant instant = Instant.ofEpochMilli(timestamp);

        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
