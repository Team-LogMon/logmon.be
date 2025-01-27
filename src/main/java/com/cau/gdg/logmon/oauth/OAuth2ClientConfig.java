package com.cau.gdg.logmon.oauth;

import com.cau.gdg.logmon.oauth.client.GoogleOAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

@Configuration
@RequiredArgsConstructor
public class OAuth2ClientConfig {

    private final RestTemplate restTemplate;

    @Bean
    public OAuth2Client googleOAuth2Client(OAuth2Properties oAuth2Properties) {
        OAuth2Properties.Provider google = oAuth2Properties.getGoogle();
        return new GoogleOAuth2Client(restTemplate,
                OAuth2Provider.GOOGLE,
                google.getClientId(),
                google.getClientSecret(),
                google.getRedirectUrl(),
                google.getAccessTokenUrl(),
                google.getAuthorizationUrl(),
                google.getUserInfoUrl());
    }

    @Bean
    public UriBuilderFactory uriBuilderFactory() {
        return new DefaultUriBuilderFactory();
    }
}
