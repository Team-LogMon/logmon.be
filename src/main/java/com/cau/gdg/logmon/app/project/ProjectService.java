package com.cau.gdg.logmon.app.project;

import com.cau.gdg.logmon.app.member.Member;
import com.cau.gdg.logmon.app.member.MemberRepository;
import com.cau.gdg.logmon.app.project.dto.ProjectCreateRequest;
import com.cau.gdg.logmon.app.user.User;
import com.cau.gdg.logmon.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public String createProject(ProjectCreateRequest request, String userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);

        Project p = Project.of(
                request.getTitle(),
                request.getDescription(),
                userId,
                request.getPricing()
        );

        String projectId = projectRepository.save(p);

        memberRepository.save(
                Member.of(
                        projectId,
                        userId,
                        true,
                        user.getEmail(),
                        List.of("See Logs", "Register Webhook"),
                        Member.Status.ACTIVE
                )
        );

        return projectId;
    }

    public List<Project> findByIdsIn(List<String> projectIds) {
        return projectRepository.findByIdsIn(projectIds);
    }


    public Project getProject(String projectId) {
        return projectRepository.findById(projectId).orElseThrow(RuntimeException::new);
    }
}
