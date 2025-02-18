package com.cau.gdg.logmon.app.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public List<Member> getMembers(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String projectId
    ) {
        if (userId != null) {
            return memberService.getMembersByUserId(userId);
        } else {
            return memberService.getMembersByProjectId(projectId);
        }
    }
}
