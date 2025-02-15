package com.cau.gdg.logmon.app.user;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private static final String COLLECTION = "Users";
    private final Firestore db;

    public void save(User user) {
        try {
            db.collection(COLLECTION).add(user).get();
        } catch (Exception e) {
            log.error("db connect error = {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findById(String userId) {
        try {
            DocumentSnapshot snapshot = db.collection(COLLECTION).document(userId).get().get();

            if (snapshot.exists()) {
                return Optional.ofNullable(snapshot.toObject(User.class));
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            CollectionReference users = db.collection(COLLECTION);

            // email 필드가 일치하는 쿼리 실행
            Query query = users.whereEqualTo("email", email);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

            // 사용자가 없을 경우 null 반환 또는 예외 처리
            if (documents.isEmpty()) {
                return Optional.empty();
            }

            // 여러 문서가 반환될 수 있으나, 일반적으로 이메일은 유니크
            DocumentSnapshot document = documents.get(0);
            User user = document.toObject(User.class);
            return Optional.ofNullable(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
