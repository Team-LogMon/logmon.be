package com.cau.gdg.logmon.app.project;

import com.cau.gdg.logmon.annotation.AuthenticationUserId;
import com.cau.gdg.logmon.app.project.dto.ProjectCreateRequest;
import com.cau.gdg.logmon.app.project.dto.ProjectCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectCreateResponse createProject(
            @AuthenticationUserId String userId,
            @RequestBody ProjectCreateRequest request) {
        String projectId =  projectService.createProject(request, userId);

        return new ProjectCreateResponse(projectId);
    }

    @GetMapping
    public List<Project> getProjects(
            @RequestParam  List<String> projectIds
    ) {
        return projectService.findByIdsIn(projectIds);
    }

    @GetMapping("/{projectId}")
    public Project getProject(@PathVariable String projectId) {
        return projectService.getProject(projectId);
    }
}
