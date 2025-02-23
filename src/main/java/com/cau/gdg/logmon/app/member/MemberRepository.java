package com.cau.gdg.logmon.app.member;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private static final String COLLECTION = "Members";
    private final Firestore db;

    public void save(Member member) {
        try {
            if (member.getId() == null) {
                db.collection(COLLECTION).add(member).get();
            } else {
                db.collection(COLLECTION).document(member.getId()).set(
                        member, SetOptions.merge()
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Member> findById(String id) {
        try {
            DocumentSnapshot snapshot = db.collection(COLLECTION).document(id).get().get();
            if (snapshot.exists()) {
                return Optional.ofNullable(snapshot.toObject(Member.class));
            } else {
                return Optional.empty();
            }

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
            QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("userId", userId)
                    .whereEqualTo("status", Member.Status.ACTIVE)
                    .get().get();
            return snapshot.getDocuments().stream().map(doc -> doc.toObject(Member.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Member> findByUserIdAndProjectId(String userId, String projectId) {
        try {
            QuerySnapshot snapshot = db.collection(COLLECTION)
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("projectId", projectId)
                    .get().get();

            if (snapshot.getDocuments().isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(snapshot.getDocuments().get(0).toObject(Member.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findByUserWithPendingStatus(String userId) {
        try {
            QuerySnapshot snapshot = db.collection(COLLECTION)
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("status", Member.Status.PENDING)
                    .get().get();

            return snapshot.getDocuments().stream().map((doc) -> doc.toObject(Member.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Member member) {
        try {
            db.collection(COLLECTION).document(member.getId()).delete().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
