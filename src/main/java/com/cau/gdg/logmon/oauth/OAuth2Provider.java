package com.cau.gdg.logmon.oauth;

public enum OAuth2Provider {
    GOOGLE,
    NAVER,
    KAKAO;

    public static OAuth2Provider from(String provider) {
        return OAuth2Provider.valueOf(provider.toUpperCase());
    }
}
