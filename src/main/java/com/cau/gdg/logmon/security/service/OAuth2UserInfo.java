package com.cau.gdg.logmon.security.service;

import java.util.Map;

import static com.cau.gdg.logmon.app.user.User.SocialType;

/**
 * 소셜 타입별 유저 정보를 가지는 Oauth2UserInfo 추상클래스
 * */
public abstract class OAuth2UserInfo {
    protected SocialType socialType;

    protected Map<String, Object> attributes;

    protected OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getNickname();

    public abstract String getImageUrl();

    public abstract String getEmail();

    public SocialType getSocialType() {
        return socialType;
    }
}
