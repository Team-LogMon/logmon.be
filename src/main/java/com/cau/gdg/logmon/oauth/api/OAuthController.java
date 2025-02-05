package com.cau.gdg.logmon.oauth.api;

import com.cau.gdg.logmon.auth.Authentication;
import com.cau.gdg.logmon.auth.AuthenticationContext;
import com.cau.gdg.logmon.oauth.OAuth2Client;
import com.cau.gdg.logmon.oauth.OAuth2Provider;
import com.cau.gdg.logmon.oauth.client.OAuth2ClientMapper;
import com.cau.gdg.logmon.oauth.model.OAuth2User;
import com.cau.gdg.logmon.oauth.model.TokenResponse;
import com.cau.gdg.logmon.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuth2ClientMapper clientMapper;
    private final JwtService jwtService;

    @GetMapping("/oauth/{provider}/login")
    public void toLoginUrl(@RequestParam String state, HttpServletResponse response, @PathVariable String provider, @Authentication AuthenticationContext ctx) throws IOException {
        log.trace("OAuthController.toLoginUrl");

        OAuth2Provider oAuth2Provider = OAuth2Provider.from(provider);

        OAuth2Client oauth2Client = clientMapper.getClient(oAuth2Provider);

        response.sendRedirect(oauth2Client.getLoginUrl(state));
    }

    @GetMapping("/oauth/{provider}/callback")
    public void handleCallback(HttpServletRequest request, HttpServletResponse response, @PathVariable String provider, String code, @RequestParam String state) throws IOException {
        log.trace("OAuthController.handleCallback");
        OAuth2Provider oAuth2Provider = OAuth2Provider.from(provider);

        OAuth2Client oauth2Client = clientMapper.getClient(oAuth2Provider);

        TokenResponse tokenResponse = oauth2Client.getToken(code);

        OAuth2User oauth2User = oauth2Client.getUser(tokenResponse.getAccessToken());

        // TODO: 사용자 인증 정보를 바탕으로 유저 정보 DB에 저장 및 관리

        // TODO:  추후 DB 유저의 식별자로 accessToken 변경

        String accessToken = jwtService.createToken(new JwtService.TokenCreateRequest(oauth2User.getId()));
        response.setHeader("Authorization", accessToken);

        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        if (state.equals("local")) {
            response.sendRedirect("http://localhost:5173/");
        } else {
            response.sendRedirect("https://logmon-4ba86.web.app/");
        }
    }
}
