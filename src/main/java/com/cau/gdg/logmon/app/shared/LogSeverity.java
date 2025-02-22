package com.cau.gdg.logmon.app.shared;

import lombok.Getter;

@Getter
public enum LogSeverity {
    TRACE("TRACE", 0, 0x808080),   // 회색 (GREY)
    DEBUG("DEBUG", 1, 0x3498DB),   // 파랑 (BLURPLE)
    INFO("INFO", 2, 0x2ECC71),     // 초록 (GREEN)
    WARNING("WARNING", 3, 0xF1C40F), // 노랑 (YELLOW)
    ERROR("ERROR", 4, 0xE74C3C);   // 빨강 (RED)

    private final String label;
    private final int level;
    private final int color;  // 디스코드 색상 (10진수 RGB 값)

    LogSeverity(String label, int level, int color) {
        this.label = label;
        this.level = level;
        this.color = color;
    }

    // ✅ 로그 레벨에 따른 디스코드 색상 반환 메서드
    public int getColor() {
        return this.color;
    }
}
