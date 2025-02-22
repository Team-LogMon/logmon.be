package com.cau.gdg.logmon.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtil {

    private static final String TOKEN = "token";
    private static final long MAX_AGE = 60 * 30; // 30 분

    /**
     * 특정 이름을 가진 쿠키 검색하는 메서드
     * @param request
     * @return Optional<Cookie>
     * */
    public static Optional<Cookie> getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 새로운 쿠키를 생성하고 HTTP 응답에 추가하는 메서드
     * @param response
     * @param token
     * */
    public static void addCookie(
            HttpServletResponse response,
            String token
    ) {
        ResponseCookie cookie = ResponseCookie.from(TOKEN, token) // 쿠키 이름과 값 설정
                .path("/") // 쿠키 전체 도메인으로 경로 설정
                .sameSite("None") // 쿠키가 같은 사이트 요청뿐만 아니라 크로스-사이트 요청에서도 전송될 수 있음
                .httpOnly(true) // XSS 공격으로부터 쿠키를 보호
                .secure(true) // https 환경에서 true 로 변경
                .maxAge(MAX_AGE) // 초단위로 쿠키 만료 지정
                .build();
        log.trace("cookie 값 설정 ={}", cookie.toString());

        response.addHeader("Set-Cookie", cookie.toString());
    }

    /**
     * 요청된 쿠키를 찾아 삭제하는 메서드
     * @param request
     * @param response
     * */
    public static void deleteCookie(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN.equals(cookie.getName())) {
                    ResponseCookie rcookie = ResponseCookie.from(TOKEN, "")
                            .path("/")
                            .sameSite("None")
                            .httpOnly(true)
                            .secure(true) // https 환경에서 true
                            .maxAge(0)
                            .build();

                     response.addHeader("Set-Cookie", rcookie.toString());
                }
            }
        }
    }
}