package com.hhplus.tdd.waitingqueue;

import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueueStatus;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import com.hhplus.tdd.waitingqueue.domain.service.WaitingQueueService;
import com.hhplus.tdd.waitingqueue.presentation.response.QueuePositionRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WaitingQueueServiceIntegrationTest {

    @Autowired
    private WaitingQueueService waitingQueueService;

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

    @Test
    void 토큰발급() {
        final Long userId = 4L;
        QueuePositionRes res = waitingQueueService.addWaitingQueue(userId);

        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueTokenOrThrow(res.getToken());

        assertAll(
                "WaitingQueue 검증",
                () -> assertNotNull(waitingQueue, "대기열 정보가 null이어서는 안 됩니다."),
                () -> assertEquals(userId, waitingQueue.getUserId(), "사용자 ID가 일치하지 않습니다."),
                () -> assertEquals(res.getToken(), waitingQueue.getToken(), "발급된 토큰이 일치하지 않습니다."),
                () -> assertEquals(WaitingQueueStatus.WAITING.toString(), waitingQueue.getTokenStatus(), "토큰 상태가 ACTIVE이어야 합니다.")
        );

    }

    @Test
    void 토큰조회() {
        final Long userId = 4L;
        QueuePositionRes res = waitingQueueService.getWaitingQueuePosition(userId);
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueTokenOrThrow(res.getToken());
        assertAll(
                "WaitingQueue 검증",
                () -> assertNotNull(waitingQueue, "대기열 정보가 null이어서는 안 됩니다."),
                () -> assertEquals(userId, waitingQueue.getUserId(), "사용자 ID가 일치하지 않습니다."),
                () -> assertEquals(res.getToken(), waitingQueue.getToken(), "발급된 토큰이 일치하지 않습니다."),
                () -> assertEquals(WaitingQueueStatus.WAITING.toString(), waitingQueue.getTokenStatus(), "토큰 상태가 ACTIVE이어야 합니다.")
        );
    }

    @Test
    void 토큰활성화처리() {
        // Given
        final Long userId = 5L;
        QueuePositionRes res = waitingQueueService.addWaitingQueue(userId);
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueTokenOrThrow(res.getToken());

        // When
        waitingQueueService.activeWaitingQueue(res.getToken());

        // Then
        WaitingQueue updatedQueue = waitingQueueRepository.getWaitingQueueTokenOrThrow(res.getToken());
        assertEquals(WaitingQueueStatus.ACTIVE.toString(), updatedQueue.getTokenStatus(), "토큰 상태가 ACTIVE이어야 합니다.");
    }

    @Test
    void 토큰만료처리() {
        // Given
        final Long userId = 6L;
        QueuePositionRes res = waitingQueueService.addWaitingQueue(userId);
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueTokenOrThrow(res.getToken());

        // When
        waitingQueueService.expiredWaitingQueue(res.getToken());

        // Then
        WaitingQueue updatedQueue = waitingQueueRepository.getWaitingQueueTokenOrThrow(res.getToken());
        assertEquals(WaitingQueueStatus.EXPIRED.toString(), updatedQueue.getTokenStatus(), "토큰 상태가 EXPIRED이어야 합니다.");
    }

}
