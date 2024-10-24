package com.hhplus.tdd.waitingqueue.domain.service;

import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueueStatus;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WaitingQueueService {

    private final WaitingQueueRepository waitingQueueRepository;

    public WaitingQueue addWaitingQueue(Long userId) {
        // WaitingQueue 엔티티 생성
        WaitingQueue waitingQueue = WaitingQueue.of(
                null,
                userId,
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusMinutes(5), // 토큰 만료 시간 설정 (5분 후)
                LocalDateTime.now(),
                WaitingQueueStatus.WAITING.toString()
        );

        WaitingQueue savedWaitingQueue = waitingQueueRepository.save(waitingQueue);
        return savedWaitingQueue;
    }

    // 스케줄러 자신 순번 조회
    public WaitingQueue getWaitingQueuePosition(Long userId) {
        return waitingQueueRepository.getWaitingQueuePosition(userId);
    }

    public WaitingQueue getWaitingQueue(String token) {
        WaitingQueue result = waitingQueueRepository.getWaitingQueueToken(token);
        if (result == null) {
            throw new CoreException(ErrorType.WAITING_QUEUE_NOT_FOUND, token);
        }
        return WaitingQueue.of(result.getQueueId(), result.getUserId(), result.getToken(), result.getTokenExpiry(), result.getTokenCreated(), result.getTokenStatus());
    }

    public boolean getWaitingQueueCheck(String token) {
        WaitingQueue result = waitingQueueRepository.getWaitingQueueToken(token);

        if (result == null) {
            return false;
        }
        return WaitingQueueStatus.ACTIVE.toString().equals(result.getTokenStatus());
    }

    // 스케줄러 대기열 활성화 처리
    public void activeWaitingQueue(String token) {
        WaitingQueue result = waitingQueueRepository.getWaitingQueueToken(token);
        result.active();
        waitingQueueRepository.save(result);
    }

    // 스케줄러 대기열 만료 처리
    public void expiredWaitingQueue(String token) {
        WaitingQueue result = waitingQueueRepository.getWaitingQueueToken(token);
        result.expire();
        waitingQueueRepository.save(result);
    }

    // 활성화 상태인 순서 조회
    public Long getLastActivePosition() {
        return waitingQueueRepository.getLastActivePositionOrThrow();
    }
}
