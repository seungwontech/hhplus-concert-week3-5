package com.hhplus.tdd.balance.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BalanceException extends RuntimeException {
    private final BalanceErrorResult errorResult;
}