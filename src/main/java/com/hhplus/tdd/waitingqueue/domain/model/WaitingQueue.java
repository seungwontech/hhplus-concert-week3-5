package com.hhplus.tdd.waitingqueue.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class WaitingQueue {
    private Long queueId;           // 대기열 ID
    private Long userId;            // 사용자 ID
    private String token;           // 토큰 값
    private LocalDateTime tokenExpiry; // 토큰 만료 시간
    private LocalDateTime tokenCreated; // 토큰 생성 시간
    private String tokenStatus;      // 토큰 상태

    public static WaitingQueue of(Long queueId, Long userId, String token, LocalDateTime tokenExpiry, LocalDateTime tokenCreated, String tokenStatus) {
        return new WaitingQueue(queueId, userId, token, tokenExpiry, tokenCreated, tokenStatus);
    }

    public void expire() {
        this.tokenStatus = WaitingQueueStatus.EXPIRED.toString();
    }

    public void active() {
        this.tokenStatus = WaitingQueueStatus.ACTIVE.toString();
    }
}
