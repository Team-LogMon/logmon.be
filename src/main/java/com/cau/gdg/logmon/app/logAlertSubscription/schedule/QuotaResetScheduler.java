package com.cau.gdg.logmon.app.logAlertSubscription.schedule;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscriptionRepository;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logAlertSubscriptions/schedule")
public class QuotaResetScheduler {

    @Value("${scheduler.key}")
    private String sKey;

    private final LogAlertSubscriptionRepository logAlertSubscriptionRepository;

    @PostMapping("/quota-reset")
    public void resetQuota(HttpServletRequest httpRequest) {
        String userSKey = httpRequest.getHeader("s-key");

        if (userSKey == null) {
            log.error("S-key not exists");
            return;
        }

        if (!sKey.equals(userSKey)) {
            log.error("S-key not matched.");
            return;
        }

        try {
            List<LogAlertSubscription> logAlertSubscriptions = logAlertSubscriptionRepository.findAll();
            LocalDate date = LocalDate.now();

            boolean isFirstDayOfMonth = date.getDayOfMonth() == 1;
            boolean isMonday = date.getDayOfWeek() == DayOfWeek.MONDAY;

            if (!isFirstDayOfMonth && !isMonday) {
                return;
            }

            for (LogAlertSubscription s: logAlertSubscriptions) {
                if (isFirstDayOfMonth) {
                    s.resetMonthlyQuota();
                }

                if (isMonday) {
                    s.resetDailyQuota();
                }
            }

            logAlertSubscriptionRepository.saveAll(logAlertSubscriptions);
        } catch (Exception e) {
            log.error("Failed to reset quota", e);
        }
    }
}
