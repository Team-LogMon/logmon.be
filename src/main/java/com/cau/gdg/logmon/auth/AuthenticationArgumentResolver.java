package com.cau.gdg.logmon.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.trace("AuthenticationArgumentResolver.supportsParameter");
        boolean hasAuthenticationAnnotation = parameter.hasParameterAnnotation(Authentication.class);
        boolean isAuthenticationContext = AuthenticationContext.class.isAssignableFrom(parameter.getParameterType());
        return hasAuthenticationAnnotation && isAuthenticationContext;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.trace("AuthenticationArgumentResolver.resolveArgument");
        return AuthenticationContextHolder.getContext();
    }
}
