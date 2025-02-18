package com.cau.gdg.logmon.app.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InviteRequest {
    private String projectId;
    private List<String> inviteeEmails;
}
