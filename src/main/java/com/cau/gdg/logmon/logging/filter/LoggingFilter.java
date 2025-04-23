package com.cau.gdg.logmon.logging.filter;

import com.cau.gdg.logmon.logging.wrapper.CachedBodyHttpServletRequest;
import com.cau.gdg.logmon.logging.wrapper.CachedBodyHttpServletResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);
            CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);

            // 요청 URL 및 헤더 로깅
            log.debug("==> [REQUEST] {} {}", wrappedRequest.getMethod(), wrappedRequest.getRequestURL());
            Enumeration<String> headerNames = wrappedRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                log.debug("==> Header [{}]: {}", headerName, wrappedRequest.getHeader(headerName));
            }

            // 요청 본문 로깅
            log.debug("==> Request Body: {}", wrappedRequest.getRequestBody());

            chain.doFilter(wrappedRequest, wrappedResponse);

            // 응답 상태 코드 및 본문 로깅
            log.debug("<== [RESPONSE] Status: {}", wrappedResponse.getStatus());
            log.debug("<== Response Body: {}", wrappedResponse.getResponseBody());
        } else {
            chain.doFilter(request, response);
        }
    }
}
