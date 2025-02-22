package com.cau.gdg.logmon.app.shared;

import lombok.Getter;

@Getter
public enum LogSeverity {
    TRACE("TRACE", 0),
    DEBUG("DEBUG", 1),
    INFO("INFO", 2),
    WARNING("WARNING", 3),
    ERROR("ERROR", 4);

    private final String label;
    private final int level;

    LogSeverity(String label, int level) {
        this.label = label;
        this.level = level;
    }
}
