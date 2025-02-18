package com.cau.gdg.logmon.annotation.resolver;

import com.cau.gdg.logmon.annotation.AuthenticationUserId;
import com.cau.gdg.logmon.security.util.CookieUtil;
import com.cau.gdg.logmon.security.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static com.cau.gdg.logmon.security.util.JwtConstants.SUB;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationUserIdArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationUserId.class)
                && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return null;
        }

        AuthenticationUserId annotation = parameter.getParameterAnnotation(AuthenticationUserId.class);
        boolean isRequired =  annotation.required();

        Optional<Cookie> tokenCookie = CookieUtil.getCookie(request);

        if (tokenCookie.isEmpty()) {
            if (isRequired) {
                log.error("Auth Exception - Token is required but missing");
                throw new RuntimeException("Authentication token is missing");
            }

            return null;
        }

        Claims claims = jwtTokenProvider.parseToken(tokenCookie.get().getValue());
        String userId = (String) claims.get(SUB);
        return userId;
    }

}
