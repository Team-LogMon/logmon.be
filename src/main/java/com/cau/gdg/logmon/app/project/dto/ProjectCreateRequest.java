package com.cau.gdg.logmon.app.project.dto;

import com.cau.gdg.logmon.app.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProjectCreateRequest {
    private String title;
    private String description;
    private Project.Pricing pricing;
}
