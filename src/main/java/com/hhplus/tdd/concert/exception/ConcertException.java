package com.hhplus.tdd.concert.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConcertException extends RuntimeException {
    private final ConcertErrorResult errorResult;
}