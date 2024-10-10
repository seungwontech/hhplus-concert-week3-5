package com.hhplus.tdd.concert.interfaces.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TokenIssueResponse {
    private Long userId;
    private String token;
    private LocalDateTime expiryTime;
    private LocalDateTime createdAt;
}
