package com.cau.gdg.logmon.security.handler;

import com.cau.gdg.logmon.app.user.User;
import com.cau.gdg.logmon.security.service.CustomOAuth2User;
import com.cau.gdg.logmon.security.util.CookieUtil;
import com.cau.gdg.logmon.security.util.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // OAuth2 인증을 통해 얻은 사용자 정보를 가져옴
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        User user = oAuth2User.getUser();

        DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();
        defaultRedirectStrategy.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);

        log.debug("OAuth2 Login 성공!");

        String token = jwtTokenProvider.createToken(user);
        CookieUtil.addCookie(response, token);
        // 서버용
        response.sendRedirect("https://logmon-4ba86.web.app/");
        // 로컬용
//        response.sendRedirect("http://localhost:8080");
    }
}
