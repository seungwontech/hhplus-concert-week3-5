package com.hhplus.tdd.waitingqueue.presentation.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class QueuePositionRes {
    private Long queueId;
    private Long userId;
    private String token;
    private Long position;
    private LocalDateTime tokenExpiry;
    private LocalDateTime tokenCreated;
    private String tokenStatus;

    public static QueuePositionRes of(Long queueId,Long userId, String token, Long position, LocalDateTime tokenExpiry, LocalDateTime tokenCreated, String tokenStatus) {
        return new QueuePositionRes(queueId, userId, token, position, tokenExpiry, tokenCreated, tokenStatus);
    }
}