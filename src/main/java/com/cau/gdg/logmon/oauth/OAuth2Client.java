package com.cau.gdg.logmon.oauth;


import com.cau.gdg.logmon.oauth.model.OAuth2User;
import com.cau.gdg.logmon.oauth.model.TokenResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Getter
@RequiredArgsConstructor
public abstract class OAuth2Client {

    protected final RestTemplate restTemplate;

    protected final OAuth2Provider provider;
    protected final String clientId;
    protected final String clientSecret;
    protected final String redirectUrl;
    protected final String accessTokenUrl;
    protected final String authorizationUrl;
    protected final String userInfoUrl;

    public abstract TokenResponse getToken(String code);

    public abstract OAuth2User getUser(String accessToken);

    public abstract String getLoginUrl(String state);


    protected final TokenResponse defaultTokenRequest(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("redirect_uri", redirectUrl);
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        return restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, TokenResponse.class).getBody();
    }
}
