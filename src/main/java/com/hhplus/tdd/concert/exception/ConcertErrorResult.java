package com.hhplus.tdd.concert.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConcertErrorResult {

    CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트를 찾을 수 없습니다."),
    CONCERT_SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트 좌석을 찾을 수 없습니다."),
    CONCERT_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트의 일정이 없습니다."),

    CONCERT_SCHEDULE_AVAILABLE_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트 예약 가능한 일정이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
