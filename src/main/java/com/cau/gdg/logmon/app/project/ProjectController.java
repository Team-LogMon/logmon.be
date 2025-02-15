package com.cau.gdg.logmon.app.project;

import com.cau.gdg.logmon.app.project.dto.ProjectCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public void createProject(@RequestBody ProjectCreateRequest request) {
        projectService.createProject(request);
    }

    @GetMapping
    public List<Project> getProjectsByUser() {
        String userId = "test-user";

        return projectService.getProjectsByUser(userId);
    }
}
