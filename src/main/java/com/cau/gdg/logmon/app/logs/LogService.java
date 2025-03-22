package com.cau.gdg.logmon.app.logs;

import com.cau.gdg.logmon.app.logs.dto.LogCreateRequest;
import com.cau.gdg.logmon.app.notification.event.LogCreatedNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void saveLog(LogCreateRequest request) {
        Log savedLog = Log.of(
                request.getProjectId(),
                request.getMessage(),
                request.getSource(),
                request.getTimeStamp(),
                request.getSeverity(),
                request.getUid(),
                request.getJsonPayload()
        );

        logRepository.save(savedLog);
        eventPublisher.publishEvent(new LogCreatedNotificationEvent(savedLog));
    }

    public List<Log> findByRange(String projectId, long start, long end) {
        return logRepository.findByTimeRange(projectId, start, end);
    }
}
