package com.cau.gdg.logmon.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class OAuth2User {
    // provider 에서 제공하는 ID
    private String id;
    private String name;
    private String profileImage;
    private String email;
}
