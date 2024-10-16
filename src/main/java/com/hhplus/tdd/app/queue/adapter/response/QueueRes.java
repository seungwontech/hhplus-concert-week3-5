package com.hhplus.tdd.app.queue.adapter.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QueueRes {
    private long userId;
    private String token;
    private LocalDateTime tokenExpiry;
    private LocalDateTime tokenCreated;
    private String tokenStatus;
}

