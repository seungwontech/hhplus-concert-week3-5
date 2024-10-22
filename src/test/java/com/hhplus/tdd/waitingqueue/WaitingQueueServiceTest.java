package com.hhplus.tdd.waitingqueue;

import com.hhplus.tdd.waitingqueue.domain.exception.WaitingQueueErrorResult;
import com.hhplus.tdd.waitingqueue.domain.exception.WaitingQueueException;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueueStatus;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import com.hhplus.tdd.waitingqueue.domain.service.WaitingQueueService;
import com.hhplus.tdd.waitingqueue.presentation.response.QueuePositionRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WaitingQueueServiceTest {

    @Mock
    private WaitingQueueRepository waitingQueueRepository;

    @InjectMocks
    private WaitingQueueService waitingQueueService;


    @Test
    void 대기열_추가_성공() {
        // Given
        Long userId = 1L;
        WaitingQueue mockQueue = WaitingQueue.of(
                1L,
                userId,
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now(),
                WaitingQueueStatus.WAITING.toString()
        );
        when(waitingQueueRepository.save(any(WaitingQueue.class))).thenReturn(mockQueue);

        // When
        QueuePositionRes result = waitingQueueService.addWaitingQueue(userId);

        // Then
        assertNotNull(result);
        assertEquals(mockQueue.getQueueId(), result.getQueueId());
        assertEquals(mockQueue.getUserId(), result.getUserId());
        verify(waitingQueueRepository, times(1)).save(any(WaitingQueue.class));
    }

    @Test
    void 대기열_위치_조회_성공() {
        // Given
        Long userId = 1L;
        WaitingQueue mockQueue = WaitingQueue.of(
                1L,
                userId,
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now(),
                WaitingQueueStatus.WAITING.toString()
        );
        when(waitingQueueRepository.getWaitingQueuePosition(userId)).thenReturn(mockQueue);

        // When
        QueuePositionRes result = waitingQueueService.getWaitingQueuePosition(userId);

        // Then
        assertNotNull(result);
        assertEquals(mockQueue.getQueueId(), result.getQueueId());
        verify(waitingQueueRepository, times(1)).getWaitingQueuePosition(userId);
    }

    @Test
    void 대기열_조회_성공() {
        // Given
        String token = "some-token";
        WaitingQueue mockQueue = WaitingQueue.of(
                1L,
                1L,
                token,
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now(),
                WaitingQueueStatus.WAITING.toString()
        );
        when(waitingQueueRepository.getWaitingQueueToken(token)).thenReturn(mockQueue);

        // When
        WaitingQueue result = waitingQueueService.getWaitingQueue(token);

        // Then
        assertNotNull(result);
        assertEquals(mockQueue.getToken(), result.getToken());
        verify(waitingQueueRepository, times(1)).getWaitingQueueToken(token);
    }

    @Test
    void 대기열_활성화_성공() {
        // Given
        String token = "some-token";
        WaitingQueue mockQueue = WaitingQueue.of(
                1L,
                1L,
                token,
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now(),
                WaitingQueueStatus.WAITING.toString()
        );
        when(waitingQueueRepository.getWaitingQueueToken(token)).thenReturn(mockQueue);
        when(waitingQueueRepository.save(any(WaitingQueue.class))).thenReturn(mockQueue);

        // When
        waitingQueueService.activeWaitingQueue(token);

        // Then
        assertEquals(WaitingQueueStatus.ACTIVE.toString(), mockQueue.getTokenStatus());
        verify(waitingQueueRepository, times(1)).getWaitingQueueToken(token);
        verify(waitingQueueRepository, times(1)).save(mockQueue);
    }

    @Test
    void 대기열_만료_성공() {
        // Given
        String token = "some-token";
        WaitingQueue mockQueue = WaitingQueue.of(
                1L,
                1L,
                token,
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now(),
                WaitingQueueStatus.ACTIVE.toString()
        );
        when(waitingQueueRepository.getWaitingQueueToken(token)).thenReturn(mockQueue);
        when(waitingQueueRepository.save(any(WaitingQueue.class))).thenReturn(mockQueue);

        // When
        waitingQueueService.expiredWaitingQueue(token);

        // Then
        assertEquals(WaitingQueueStatus.EXPIRED.toString(), mockQueue.getTokenStatus());
    }

    @Test
    void 활성화_상태인_순서_조회_성공() {
        // Given
        Long expectedPosition = 1L;
        when(waitingQueueRepository.getLastActivePosition()).thenReturn(expectedPosition);

        // When
        Long result = waitingQueueService.getLastActivePosition();

        // Then
        assertEquals(expectedPosition, result);
        verify(waitingQueueRepository, times(1)).getLastActivePosition();
    }

    @Test
    public void 대기열_찾지못함_예외처리() {
        String token = "invalid";

        when(waitingQueueRepository.getWaitingQueueToken(token)).thenReturn(null);

        WaitingQueueException exception = assertThrows(WaitingQueueException.class, () -> {
            waitingQueueService.getWaitingQueue(token);
        });
        assertEquals(WaitingQueueErrorResult.WAITINGQUEUE_NOT_FOUND, exception.getWaitingQueueErrorResult());
    }
}
