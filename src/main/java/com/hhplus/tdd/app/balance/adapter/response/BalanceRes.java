package com.hhplus.tdd.app.balance.adapter.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceRes {
    private long userId;
    private long amount;
}
