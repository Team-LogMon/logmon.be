package com.cau.gdg.logmon.app.logs.dto;

import com.cau.gdg.logmon.app.shared.LogSeverity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class LogCreateRequest {
    private String projectId;
    private String message;
    private Long timeStamp;
    private String source;
    private LogSeverity severity;
    private Map<String,Object> jsonPayload;
}
