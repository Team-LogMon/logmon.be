package com.cau.gdg.logmon.config;

import com.cau.gdg.logmon.annotation.resolver.AuthenticationUserIdArgumentResolver;
import com.cau.gdg.logmon.security.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationUserIdArgumentResolverConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationUserIdArgumentResolver(jwtTokenProvider));
    }
}
