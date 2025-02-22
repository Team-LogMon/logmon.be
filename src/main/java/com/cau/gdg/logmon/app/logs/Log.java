package com.cau.gdg.logmon.app.logs;

import com.cau.gdg.logmon.app.shared.LogSeverity;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;

import java.util.Map;

@Getter
public class Log {
    @DocumentId
    private String id;
    private String projectId;
    private String message;
    private String source;
    private long timeStamp;
    private LogSeverity severity;
    private String uid;
    private Map<String, Object> jsonPayload;

    public static Log of(
            String projectId,
            String message,
            String source,
            long timeStamp,
            LogSeverity severity,
            String uid,
            Map<String, Object> jsonPayload
    ) {
        Log log = new Log();
        log.projectId = projectId;
        log.message = message;
        log.source = source;
        log.timeStamp = timeStamp;
        log.severity = severity;
        log.uid = uid;
        log.jsonPayload = jsonPayload;
        return log;
    }


}
