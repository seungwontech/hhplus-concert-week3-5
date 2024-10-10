package com.hhplus.tdd.concert.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {
    private Long userId;
    private String token;
    private LocalDateTime expiryTime;
    private LocalDateTime createdAt;
}
