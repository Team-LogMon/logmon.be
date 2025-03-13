package com.cau.gdg.logmon.app.member;

import com.cau.gdg.logmon.annotation.AuthenticationUserId;
import com.cau.gdg.logmon.app.member.dto.AcceptRequest;
import com.cau.gdg.logmon.app.member.dto.InviteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public List<Member> getMembers(
            @AuthenticationUserId(required = false) String userId
    ) {
        return memberService.getMembersByUserId(userId);
    }

    @GetMapping("/project/{projectId}")
    public List<Member> getMembersByProjectId(
            @PathVariable String projectId
    ) {
        return memberService.getMembersByProjectId(projectId);
    }

    @GetMapping("/invite")
    public List<Member> showInvitations(
            @AuthenticationUserId String userId
    ) {
        return memberService.getInvitations(userId);
    }

    @PostMapping("/invite")
    public void invite(
            @AuthenticationUserId String userId,
            @RequestBody InviteRequest request
    ) {
        memberService.createInvitations(
                userId,
                request.getInviteeEmails(),
                request.getProjectId()
        );
    }

    @PostMapping("/accept")
    public void accept(
            @AuthenticationUserId String userId,
            @RequestBody AcceptRequest request
    ) {
        memberService.acceptInvitation(userId, request.getProjectId());
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(
            @AuthenticationUserId String userId,
            @PathVariable String memberId) {
        memberService.deleteMember(userId, memberId);
    }
}
