package com.cau.gdg.logmon.config;

import com.cau.gdg.logmon.security.filter.CustomAuthenticationFilter;
import com.cau.gdg.logmon.security.handler.CustomLogoutHandler;
import com.cau.gdg.logmon.security.handler.OAuth2LoginFailureHandler;
import com.cau.gdg.logmon.security.handler.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomAuthenticationFilter customAuthenticationFilter;
    private final OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
        http.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)

                // 토큰이 없는 상태에서 요청이 오는 정보들을 열어
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/login/oauth2/google").permitAll()
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/favicon.co").permitAll()
                                .anyRequest().authenticated()

                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .authorizationEndpoint(
                                        authorization ->
                                                authorization.authorizationRequestResolver(customAuthorizationRequestResolver)
                                )
                                .successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)

                )
                // 세션을 사용 안함 STATELESS 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(customAuthenticationFilter, LogoutFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // post mapping
                        .addLogoutHandler(customLogoutHandler)
                );

        return http.build();
    }
}
