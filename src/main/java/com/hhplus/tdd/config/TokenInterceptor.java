package com.hhplus.tdd.config;

import com.hhplus.tdd.waitingqueue.domain.service.WaitingQueueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final WaitingQueueService waitingQueueService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (!waitingQueueService.getWaitingQueueCheck(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
