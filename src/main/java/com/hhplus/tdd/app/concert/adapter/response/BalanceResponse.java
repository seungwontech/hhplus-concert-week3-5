package com.hhplus.tdd.app.concert.adapter.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceResponse {
    private Long userId;
    private int amount;
}
