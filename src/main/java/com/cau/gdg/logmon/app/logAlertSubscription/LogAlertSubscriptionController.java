package com.cau.gdg.logmon.app.logAlertSubscription;

import com.cau.gdg.logmon.app.logAlertSubscription.dto.LogAlertSubscriptionCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logAlertSubscriptions")
@RequiredArgsConstructor
public class LogAlertSubscriptionController {

    private final LogAlertSubscriptionService logAlertSubscriptionService;

    @GetMapping
    public List<LogAlertSubscription> getLogAlertSubscriptions(
            @RequestParam String projectId
    ) {
        return logAlertSubscriptionService.findByProjectId(projectId);
    }

    @PostMapping
    public void createLogAlertSubscription(@RequestBody LogAlertSubscriptionCreateRequest request) {
        logAlertSubscriptionService.createLogAlertSubscription(request);
    }

    @DeleteMapping("/{id}")
    public void deleteLogAlertSubscription(@PathVariable String id) {
        logAlertSubscriptionService.deleteLogAlertSubscription(id);
    }
}
