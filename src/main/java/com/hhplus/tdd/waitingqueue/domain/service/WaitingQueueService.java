package com.hhplus.tdd.waitingqueue.domain.service;

import com.hhplus.tdd.waitingqueue.domain.exception.WaitingQueueErrorResult;
import com.hhplus.tdd.waitingqueue.domain.exception.WaitingQueueException;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueueStatus;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import com.hhplus.tdd.waitingqueue.presentation.response.QueuePositionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WaitingQueueService {

    private final WaitingQueueRepository waitingQueueRepository;

    public QueuePositionRes addWaitingQueue(Long userId) {
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

        return QueuePositionRes.of(
                savedWaitingQueue.getQueueId(),
                savedWaitingQueue.getUserId(),
                savedWaitingQueue.getToken(),
                savedWaitingQueue.getQueueId(),
                savedWaitingQueue.getTokenExpiry(),
                savedWaitingQueue.getTokenCreated(),
                savedWaitingQueue.getTokenStatus()
        );
    }

    // 스케줄러 자신 순번 조회
    public QueuePositionRes getWaitingQueuePosition(Long userId) {

        WaitingQueue result = waitingQueueRepository.getWaitingQueuePosition(userId);

        return QueuePositionRes.of(
                result.getQueueId(),
                result.getUserId(),
                result.getToken(),
                result.getQueueId(),
                result.getTokenExpiry(),
                result.getTokenCreated(),
                result.getTokenStatus()
        );
    }

    public WaitingQueue getWaitingQueue(String token) {
        WaitingQueue result = waitingQueueRepository.getWaitingQueueToken(token);
        if (result == null) {
            throw new WaitingQueueException(WaitingQueueErrorResult.WAITINGQUEUE_NOT_FOUND);
        }
        return WaitingQueue.of(result.getQueueId(), result.getUserId(), result.getToken(), result.getTokenExpiry(), result.getTokenCreated(), result.getTokenStatus());
    }

    // 스케줄러 대기열 활성화 처리
    public void activeWaitingQueue(String token) {
        WaitingQueue result = waitingQueueRepository.getWaitingQueueToken(token);
        result.of(result.getQueueId(),result.getUserId(),result.getToken(),result.getTokenExpiry(),result.getTokenCreated(),WaitingQueueStatus.ACTIVE.toString());
        waitingQueueRepository.save(result);
    }

    // 스케줄러 대기열 만료 처리
    public void expiredWaitingQueue(String token) {
        WaitingQueue result = waitingQueueRepository.getWaitingQueueToken(token);
        result.of(result.getQueueId(),result.getUserId(),result.getToken(),result.getTokenExpiry(),result.getTokenCreated(),WaitingQueueStatus.EXPIRED.toString());
        waitingQueueRepository.save(result);
    }

    // 활성화 상태인 순서 조회
    public Long getLastActivePosition(){
        return waitingQueueRepository.getLastActivePosition();
    }
}
