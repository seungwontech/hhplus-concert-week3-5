package com.hhplus.tdd.concert.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConcertPaymentReq {
    private Long userId;
    private Long[] concertReservationId;
    private Long[] concertSeatIds;

    public static ConcertPaymentReq of(Long userId, Long[] concertReservationId, Long[] concertSeatIds) {
        return new ConcertPaymentReq(userId, concertReservationId, concertSeatIds);
    }
}
