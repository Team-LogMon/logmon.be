package com.cau.gdg.logmon.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

        log.debug("소셜 로그인에 실패했습니다. 에러 메시지 : {}", exception.getMessage());


        String state = request.getParameter("state");

        if (state.equals("production")) {
            response.sendRedirect("https://logmon-4ba86.web.app?errorText=" + exception.getMessage());
        } else if (state.equals("local")) {
            response.sendRedirect("http://localhost:5173?errorText=" + exception.getMessage());
        } else {
            throw new RuntimeException("state not found");
        }
    }

}
