package com.hhplus.tdd.waitingqueue.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WaitingQueueException extends RuntimeException {
    private final WaitingQueueErrorResult waitingQueueErrorResult;
}