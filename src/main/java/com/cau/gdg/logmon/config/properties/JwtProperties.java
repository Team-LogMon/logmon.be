package com.cau.gdg.logmon.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String secret;
    private final long expiration;

    public JwtProperties(String secret, long expiration) {
        if (secret == null || secret.length() < 32) { // 256 bits = 32 bytes
            throw new IllegalArgumentException("Secret key must be at least 256 bits (32 characters) long");
        }
        this.secret = secret;
        this.expiration = expiration;
    }
}
