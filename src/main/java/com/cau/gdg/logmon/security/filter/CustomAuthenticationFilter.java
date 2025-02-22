package com.cau.gdg.logmon.security.filter;

import com.cau.gdg.logmon.app.user.User;
import com.cau.gdg.logmon.app.user.UserRepository;
import com.cau.gdg.logmon.security.util.CookieUtil;
import com.cau.gdg.logmon.security.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static com.cau.gdg.logmon.security.util.JwtConstants.SUB;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private static final String ROLE_ = "ROLE_";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 예: 헤더에서 토큰 추출
        Cookie cookie = CookieUtil.getCookie(request)
                .orElse(null);

        if (cookie != null) {
            // 1. 토큰에서 userId를 추출하여 Authentication 객체 생성
            String token = cookie.getValue();
            log.trace("=== 로그인 token = {} === ", token);

            // 2. 토큰 검증하기
            Authentication authentication = getAuthentication(token);

            // 3. SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 4. 접속 시간 연장(Cookie 시간 만큼)
            log.trace("=== 로그인 상태 - 쿠키 연장===");
            CookieUtil.addCookie(response, token);
        } else {
            CookieUtil.deleteCookie(request, response);
            log.trace("=== 로그인 상태가 아님===");
        }
        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        Claims claims = jwtTokenProvider.parseToken(token);
        String userId = (String) claims.get(SUB);
        log.trace("=== userId{} === ", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_ + user.getRoles().getKey());

        // userId를 principal로, 빈 권한 리스트를 authorities로 설정하여 Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(
                userId,
                null,
                Collections.singleton(authority)
        );
    }


}
