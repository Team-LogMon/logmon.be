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

@Slf4j
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);
            CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);

            log.info("==> Request Body: {}", wrappedRequest.getRequestBody());

            chain.doFilter(wrappedRequest, wrappedResponse);

            log.info("<== Response Body: {}", wrappedResponse.getResponseBody());
        } else {
            chain.doFilter(request, response);
        }
    }
}


