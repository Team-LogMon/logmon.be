package com.cau.gdg.logmon.app.project;

import com.cau.gdg.logmon.app.project.dto.ProjectCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public void createProject(ProjectCreateRequest request) {
        Project p = Project.of(
                request.getTitle(),
                request.getDescription(),
                "test-user",
                request.getPricing()
        );

        projectRepository.save(p);
    }

    public List<Project> getProjectsByUser(String userId) {
        return projectRepository.findByUserId(userId);
    }
}
