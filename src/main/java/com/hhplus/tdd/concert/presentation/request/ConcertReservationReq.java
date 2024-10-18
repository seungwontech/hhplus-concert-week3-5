package com.hhplus.tdd.concert.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConcertReservationReq {
    private Long userId;
    private Long[] concertSeatIds;

    public static ConcertReservationReq of(Long userId, Long[] concertSeatIds) {
        return new ConcertReservationReq(userId, concertSeatIds);
    }
}
