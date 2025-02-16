package com.cau.gdg.logmon.app.user;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @RequiredArgsConstructor
    public enum SocialType {
        GOOGLE;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Role {
        ADMIN("ADMIN", "관리자"),
        USER("USER", "유저");

        private final String key;
        private final String des;
    }

    @Getter
    @RequiredArgsConstructor
    enum UserStatus {
        ACTIVE, // 정상
        DORMANT, // 휴먼
        WITHDRAWN, // 탈퇴
        BANNED // 밴
    }

    @DocumentId
    private String id; //firebaseId;
    private String name;
    private String email;
    private SocialType socialType; // 소셜 타입
    private String profileImageURL;
    private Role roles; // MANAGER, USER,
    private Long createdAt; // 생성 시간
    private Long updatedAt; // 수정 시간
    private UserStatus status; // 휴먼, 정상, 탈퇴 여부

    public static User of(
            String name,
            String email,
            SocialType socialType,
            String profileImageURL
    ) {
        long now = System.currentTimeMillis();
        return User.builder()
                .name(name)
                .email(email)
                .socialType(socialType)
                .profileImageURL(profileImageURL)
                .roles(Role.USER)
                .createdAt(now)
                .updatedAt(now)
                .status(UserStatus.ACTIVE)
                .build();
    }


}
