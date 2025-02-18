package com.cau.gdg.logmon.app.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Member {

    public enum Status {
        ACTIVE, DEACTIVE, PENDING
    }

    @DocumentId
    private String id;

    private String projectId;
    private String userId;

    private boolean owner;
    //Denormalize
    private String userEmail;
    private List<String> permissions = new ArrayList<>();
    private Status status;

    private long createdAt;
    private long updatedAt;


    public static Member of(
            String projectId,
            String userId,
            boolean isOwner,
            String userEmail,
            List<String> permissions,
            Status status
    ) {
        long now = System.currentTimeMillis();
        Member member = new Member();
        member.projectId = projectId;
        member.userId = userId;
        member.owner = isOwner;
        member.userEmail = userEmail;
        member.permissions = permissions;
        member.status = status;
        member.createdAt = now;
        member.updatedAt = now;

        return member;
    }

    public void acceptInvitation() {
        this.status = Status.ACTIVE;
        this.updatedAt = System.currentTimeMillis();
    }
}


