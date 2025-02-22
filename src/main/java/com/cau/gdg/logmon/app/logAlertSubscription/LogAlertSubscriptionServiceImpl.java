package com.cau.gdg.logmon.app.logAlertSubscription;

import com.cau.gdg.logmon.app.logAlertSubscription.dto.LogAlertSubscriptionCreateRequest;
import com.cau.gdg.logmon.app.shared.LogSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogAlertSubscriptionServiceImpl implements LogAlertSubscriptionService {

    private final LogAlertSubscriptionRepository logAlertSubscriptionRepository;

    @Override
    public List<LogAlertSubscription> findByProjectId(String projectId) {
        return logAlertSubscriptionRepository.findByProjectId(projectId);
    }

    @Override
    public void createLogAlertSubscription(LogAlertSubscriptionCreateRequest request) {
        logAlertSubscriptionRepository.save(
                LogAlertSubscription.of(
                        request.getProjectId(),
                        request.getName(),
                        request.getPlatform(),
                        request.getUrl(),
                        request.getAlertThreshold()
                )
        );
    }

    @Override
    public void deleteLogAlertSubscription(String id) {
        logAlertSubscriptionRepository.delete(id);
    }

    @Override
    public List<LogAlertSubscription> getNotifiableSubscription(String projectId, LogSeverity alertThreshold) {
        return null;
    }
}
