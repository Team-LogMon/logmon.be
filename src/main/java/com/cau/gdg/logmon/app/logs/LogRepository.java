package com.cau.gdg.logmon.app.logs;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogRepository {

    private static final String COLLECTION = "Logs";
    private final Firestore db;

    public void save(Log log) {
        try {
            db.collection(COLLECTION).add(log).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Log> findByTimeRange(String projectId, long start, long end) {
        try {
            QuerySnapshot querySnapshot = db.collection(COLLECTION)
                    .whereEqualTo("projectId", projectId)
                    .whereGreaterThan("timeStamp", start)
                    .whereLessThan("timeStamp", end)
                    .orderBy("timeStamp", Query.Direction.ASCENDING)
                    .get().get();

            return querySnapshot.getDocuments().stream().map((doc) -> doc.toObject(Log.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
