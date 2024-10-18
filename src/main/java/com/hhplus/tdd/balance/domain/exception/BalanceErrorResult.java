package com.hhplus.tdd.balance.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BalanceErrorResult {

    BALANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "잔액을 찾을 수 없습니다."),
    BALANCE_LIMIT_AMOUNT(HttpStatus.BAD_REQUEST, "충전 금액이 초과되었습니다."),
    BALANCE_LESS_THAN_ZERO(HttpStatus.BAD_REQUEST, "충전 금액은 0보다 커야 합니다."),
    BALANCE_EXCEEDS_AVAILABLE(HttpStatus.BAD_REQUEST, "사용하려는 금액이 잔액을 초과할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
