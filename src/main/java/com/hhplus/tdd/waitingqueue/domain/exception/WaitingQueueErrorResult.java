package com.hhplus.tdd.waitingqueue.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WaitingQueueErrorResult {

    WAITINGQUEUE_NOT_FOUND(HttpStatus.NOT_FOUND, "대기열을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
