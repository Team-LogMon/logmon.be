package com.cau.gdg.logmon.app.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMembersByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    public List<Member> getMembersByProjectId(String projectId) {
        return memberRepository.findByProjectId(projectId);
    }
}
