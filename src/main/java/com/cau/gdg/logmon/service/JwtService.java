package com.cau.gdg.logmon.service;

import com.cau.gdg.logmon.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final long EXPIRATION_TIME; // 7 days
    private final SecretKey key;

    public JwtService(
            JwtProperties jwtProperties
    ) {
        this.EXPIRATION_TIME = jwtProperties.getExpiration();
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    @Getter
    @AllArgsConstructor
    public static class TokenCreateRequest {
        private String id;
    }

    public String createToken(TokenCreateRequest request) {
        return Jwts.builder()
                .subject(request.getId())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class TokenParseResponse {
        private final String id;
    }

    public TokenParseResponse parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return new TokenParseResponse(
                claims.getSubject()
        );

    }
}

