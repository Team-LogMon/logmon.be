package com.cau.gdg.logmon.app.member;

import com.cau.gdg.logmon.app.user.User;
import com.cau.gdg.logmon.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public List<Member> getMembersByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    public List<Member> getMembersByProjectId(String projectId) {
        return memberRepository.findByProjectId(projectId);
    }

    public List<Member> getInvitations(String userId) {
        return memberRepository.findByUserWithPendingStatus(userId);
    }

    public Member getMember(String id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public void createInvitations(String invitorId, List<String> inviteeEmails, String projectId) {
        for (String email: inviteeEmails) {
            try {
                User invitee = userRepository.findByEmail(email).orElseThrow();

                memberRepository.save(
                        Member.of(
                                projectId,
                                invitee.getId(),
                                false,
                                invitee.getEmail(),
                                List.of("See Logs", "Register Webhook"),
                                Member.Status.PENDING
                        )
                );

            } catch (Exception e) {
                //just continue
            }
        }
    }

    public void acceptInvitation(String inviteeId, String projectId) {
        Member member = memberRepository.findByUserIdAndProjectId(inviteeId, projectId).orElseThrow();
        member.acceptInvitation();
        memberRepository.save(member);
    }

    public void deleteMember(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        memberRepository.delete(member);
    }
}
