package com.hhplus.tdd.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    BALANCE_NOT_FOUND(ErrorCode.NOT_FOUND, "잔액을 찾을 수 없습니다.", LogLevel.WARN),
    BALANCE_LIMIT_AMOUNT(ErrorCode.CLIENT_ERROR, "충전 금액이 초과되었습니다.", LogLevel.INFO),
    BALANCE_LESS_THAN_ZERO(ErrorCode.CLIENT_ERROR, "충전 금액은 0보다 커야 합니다.", LogLevel.INFO),
    BALANCE_EXCEEDS_AVAILABLE(ErrorCode.CLIENT_ERROR, "사용하려는 금액이 잔액을 초과할 수 없습니다.", LogLevel.INFO),

    CONCERT_NOT_FOUND(ErrorCode.NOT_FOUND, "콘서트를 찾을 수 없습니다.", LogLevel.WARN),

    CONCERT_SEAT_NOT_FOUND(ErrorCode.NOT_FOUND, "콘서트 좌석을 찾을 수 없습니다.", LogLevel.WARN),
    CONCERT_SEAT_AVAILABLE_NOT_FOUND(ErrorCode.NOT_FOUND, "콘서트 예약 가능한 좌석이 없습니다.", LogLevel.INFO),
    CONCERT_SEAT_ALREADY_RESERVED(ErrorCode.NOT_FOUND, "이미 예약된 좌석입니다.", LogLevel.INFO),

    CONCERT_SCHEDULE_NOT_FOUND(ErrorCode.NOT_FOUND, "콘서트의 일정이 없습니다.", LogLevel.WARN),
    CONCERT_SCHEDULE_AVAILABLE_NOT_FOUND(ErrorCode.NOT_FOUND, "콘서트 예약 가능한 일정이 없습니다.", LogLevel.WARN),

    CONCERT_RESERVATION_NOT_FOUND(ErrorCode.NOT_FOUND, "예약한 좌석이 없습니다.", LogLevel.WARN),

    DUPLICATE_CONCERT_PAYMENTS(ErrorCode.CLIENT_ERROR, "예약한 좌석이 없습니다.", LogLevel.ERROR),

    WAITING_QUEUE_NOT_FOUND(ErrorCode.NOT_FOUND, "대기열을 찾을 수 없습니다.", LogLevel.WARN);



    private final ErrorCode errorCode;
    private final String message;
    private final LogLevel logLevel;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}
