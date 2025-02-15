package com.cau.gdg.logmon.security.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // OAuth2 인증을 통해 얻은 사용자 정보를 가져옴
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();
        defaultRedirectStrategy.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);

        log.debug("OAuth2 Login 성공!");

        Map<String, Object> attributes = oAuth2User.getAttributes();
        for (Map.Entry<String, Object> stringObjectEntry : attributes.entrySet()) {
            System.out.print("key= " + stringObjectEntry.getKey());
            System.out.println(", value = " + stringObjectEntry.getValue());
        }
        /**
         *
         * key= sub, value = 112176940481278314841
         * key= name, value = 이봉승
         * key= given_name, value = 봉승
         * key= family_name, value = 이
         * key= picture, value = https://lh3.googleusercontent.com/a/ACg8ocIRq0uUjQiSQbhgJV6rbI_PznX9XzmzrtfUYtifIuDgDnbZJegE=s96-c
         * key= email, value = yui5227@gmail.com
         * key= email_verified, value = true
         * */

        // oauth2 인증으로만 사용하는 거면 별도의 cookie 리턴 해주고.
        // 최초로그인 시 유저 저장 로직 추가
        // 1.자동 회원가입 로직 필요
        // 필수 정보 검증 필요하고

        // 2. 토큰 관리 필요하기

        // 3. 보안 및 사용자 경험 최초 로그인 안내 혹은 동의 및 추가정보 수집이 필요함.

        Object principal = authentication.getPrincipal();
        System.out.println(principal.toString());

        // 서버용
        //response.sendRedirect("https://logmon-4ba86.web.app/");
        // 로컬용
        response.sendRedirect("http://localhost:8080");
    }
}
