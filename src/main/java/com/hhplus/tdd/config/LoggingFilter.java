package com.hhplus.tdd.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpServletResponse);

        // 요청 로깅
        logRequestDetails(wrappedRequest);

        // 필터 체인 실행
        chain.doFilter(wrappedRequest, wrappedResponse);

        // 응답 로깅
        logResponseDetails(wrappedResponse);

        // 응답 내용 전송
        wrappedResponse.copyBodyToResponse();
    }

    private void logRequestDetails(ContentCachingRequestWrapper request) {
        // 요청에 대한 필요한 정보 로깅 (예: URL, 헤더, 바디 등)
        log.info("Request URL: " + request.getRequestURI());
        log.info("Request Method: " + request.getMethod());

        String requestBody = new String(request.getContentAsByteArray());
        log.info("Request Body: " + requestBody);
    }

    private void logResponseDetails(ContentCachingResponseWrapper response) {
        // 응답에 대한 필요한 정보 로깅 (예: 상태 코드, 헤더, 바디 등)
        log.info("Response Status: " + response.getStatus());

        String responseBody = new String(response.getContentAsByteArray());
        log.info("Response Body: " + responseBody);
    }
}
