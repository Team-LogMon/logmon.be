package com.cau.gdg.logmon.app.logAlertSubscription;

import com.google.cloud.firestore.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogAlertSubscriptionRepository {

    private static final String COLLECTION = "LogAlertSubscriptions";
    private final Firestore db;

    public void save(LogAlertSubscription logAlertSubscription) {
        try {
            if (logAlertSubscription.getId() == null) {
                db.collection(COLLECTION).add(logAlertSubscription).get();
            } else {
                db.collection(COLLECTION)
                        .document(logAlertSubscription.getId())
                        .set(logAlertSubscription, SetOptions.merge())
                        .get();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(List<LogAlertSubscription> logAlertSubscriptions) {
        try {
            WriteBatch batch = db.batch();

            for (LogAlertSubscription sub: logAlertSubscriptions) {
                batch.set(
                        db.collection(COLLECTION).document(sub.getId()), sub
                );
            }

            batch.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<LogAlertSubscription> findByProjectId(String projectId) {
        try {
            QuerySnapshot snapshot = db.collection(COLLECTION)
                    .whereEqualTo("projectId", projectId)
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .get().get();

            return snapshot.getDocuments().stream().map(doc -> doc.toObject(LogAlertSubscription.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        try {
            db.collection(COLLECTION).document(id).delete().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
