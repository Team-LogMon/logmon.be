package com.cau.gdg.logmon.security.util;

import com.cau.gdg.logmon.app.user.User;
import com.cau.gdg.logmon.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final long EXPIRATION_TIME;
    private final SecretKey key;

    public JwtTokenProvider(
            JwtProperties jwtProperties
    ) {
        this.EXPIRATION_TIME = jwtProperties.getExpiration();
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createToken(User user) {
        return Jwts.builder()
                .subject(user.getId())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
