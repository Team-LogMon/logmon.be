package com.cau.gdg.logmon.app.project;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private static final String COLLECTION = "Projects";
    private final Firestore db;

    public void save(Project project) {
        try {
            db.collection(COLLECTION).add(project).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Project> findById(String projectId) {
        try {
            DocumentSnapshot snapshot = db.collection(COLLECTION).document(projectId).get().get();

            if (snapshot.exists()) {
                return Optional.of(snapshot.toObject(Project.class));
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Project> findByUserId(String userId) {
        try {
            QuerySnapshot ownerSnapshot = db.collection(COLLECTION)
                    .whereEqualTo("owner", userId).get().get();

            QuerySnapshot collaboratorSnapshot = db.collection(COLLECTION)
                    .whereArrayContains("collaborators", userId).get().get();

            return Stream.concat(
                        ownerSnapshot.getDocuments()
                            .stream().map(d -> d.toObject(Project.class)),
                        collaboratorSnapshot.getDocuments()
                            .stream().map(d -> d.toObject(Project.class))
                    ).toList();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
