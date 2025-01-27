package com.cau.gdg.logmon.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ToString
@Getter
@ConfigurationProperties(prefix = "oauth2")
@AllArgsConstructor(onConstructor = @__({@ConstructorBinding}))
public class OAuth2Properties {

    @ToString
    @Getter
    @AllArgsConstructor(onConstructor = @__({@ConstructorBinding}))
    public static class Provider {
        private String clientId;
        private String clientSecret;
        private String redirectUrl;
        private String authorizationUrl;
        private String accessTokenUrl;
        private String userInfoUrl;
    }

    private Provider google;
}
