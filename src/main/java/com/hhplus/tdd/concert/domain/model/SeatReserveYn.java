package com.hhplus.tdd.concert.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class SeatReserveYn {
    private Long concertSeatId; // 콘서트 좌석 ID
    private String reserveYn;    // 예약 여부 (Y, N)
}