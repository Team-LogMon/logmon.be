package com.cau.gdg.logmon.auth;

import com.cau.gdg.logmon.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final JwtService jwtService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.trace("AuthenticationFilter.doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String token = null;
//        for(Cookie c : httpRequest.getCookies()) {
//            if (c.getName().equals("accessToken")) {
//                token = c.getValue();
//            }
//        }

        if (token != null) {
            JwtService.TokenParseResponse parsedResponse = jwtService.parseToken(token);
            AuthenticationContextHolder.setContext(new AuthenticationContext(
                    parsedResponse.getId()
            ));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        AuthenticationContextHolder.clearContext();
        Filter.super.destroy();
    }
}
