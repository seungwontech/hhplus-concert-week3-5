package com.hhplus.tdd.concert.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceResponse {
    private Long userId;
    private int amount;
}
