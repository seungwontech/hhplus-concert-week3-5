package com.hhplus.tdd.balance.presentation.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class BalanceRes {
    private Long userId;
    private int balanceAmount;
    private LocalDateTime updatedAt;
}
