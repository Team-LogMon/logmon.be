package com.cau.gdg.logmon.app.logAlertSubscription;

import com.cau.gdg.logmon.app.logAlertSubscription.dto.LogAlertSubscriptionCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogAlertSubscriptionService {

    private final LogAlertSubscriptionRepository logAlertSubscriptionRepository;

    public List<LogAlertSubscription> findByProjectId(String projectId) {
        return logAlertSubscriptionRepository.findByProjectId(projectId);
    }

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
}
