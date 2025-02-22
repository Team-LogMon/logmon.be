package com.cau.gdg.logmon.app.notification;


import com.google.cloud.firestore.Firestore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NotificationRepository {

    private static final String COLLECTION = "Notifications";
    private final Firestore db;

    public void save(Notification notification) {
        try {
            db.collection(COLLECTION).add(notification).get();
        } catch (Exception e) {
            log.error("db connect error = {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
