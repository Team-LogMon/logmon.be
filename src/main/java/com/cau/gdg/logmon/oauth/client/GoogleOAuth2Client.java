package com.cau.gdg.logmon.oauth.client;

import com.cau.gdg.logmon.oauth.OAuth2Client;
import com.cau.gdg.logmon.oauth.OAuth2Provider;
import com.cau.gdg.logmon.oauth.model.OAuth2User;
import com.cau.gdg.logmon.oauth.model.TokenResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GoogleOAuth2Client extends OAuth2Client {

    public GoogleOAuth2Client(
            RestTemplate restTemplate,
            OAuth2Provider provider,
            String clientId,
            String clientSecret,
            String redirectUrl,
            String accessTokenUrl,
            String authorizationUrl,
            String userInfoUrl
    ) {
        super(restTemplate, provider, clientId, clientSecret, redirectUrl, accessTokenUrl, authorizationUrl, userInfoUrl);
    }

    @Override
    public TokenResponse getToken(String code) {
        return this.defaultTokenRequest(code);
    }

    @Override
    public OAuth2User getUser(String accessToken) {
        try {
            // UserInfo API 호출 URL
            String url = userInfoUrl + "?access_token=" + accessToken;

            // UserInfo API 호출
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            // 사용자 정보 추출
            String id = (String) response.get("sub");
            String email = (String) response.get("email");
            String name = (String) response.get("name");
            String picture = (String) response.get("picture");

            // OAuth2User 객체 생성
            return new OAuth2User(id, name, picture, email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user information: " + e.getMessage(), e);
        }
    }

    @Override
    public String getLoginUrl(String state) {
        return authorizationUrl +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUrl +
                "&response_type=" + "code" +
                "&scope=" + "email profile" +
                "&access_type=" + "offline" +
                "&prompt=" + "consent" +
                "&state=" + state;
    }
}
