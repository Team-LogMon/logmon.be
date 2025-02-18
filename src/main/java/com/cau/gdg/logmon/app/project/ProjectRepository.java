package com.cau.gdg.logmon.app.project;

import com.google.cloud.firestore.*;
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

    /**
     *
     * @param project
     * @return id
     */
    public String save(Project project) {
        try {
            DocumentReference docRef = db.collection(COLLECTION).add(project).get();
            return docRef.getId();
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

    public List<Project> findByIdsIn(List<String> projectIds) {
        try {
            QuerySnapshot snapshot = db.collection(COLLECTION).whereIn(FieldPath.documentId(), projectIds).get().get();

            return snapshot.getDocuments().stream().map(doc -> doc.toObject(Project.class)).toList();
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
