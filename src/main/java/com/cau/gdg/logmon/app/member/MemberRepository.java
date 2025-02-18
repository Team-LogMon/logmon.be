package com.cau.gdg.logmon.app.member;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private static final String COLLECTION = "Members";
    private final Firestore db;

    public void save(Member member) {
        try {
            db.collection(COLLECTION).add(member).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findByProjectId(String projectId) {
        try {
            QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("projectId", projectId).get().get();
            return snapshot.getDocuments().stream().map(doc -> doc.toObject(Member.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findByUserId(String userId) {
        try {
            QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("userId", userId).get().get();
            return snapshot.getDocuments().stream().map(doc -> doc.toObject(Member.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
