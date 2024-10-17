package com.hhplus.tdd.concert.presentation.request;

import lombok.Getter;

@Getter
public class ConcertPaymentReq {
    private Long userId;
    private Long[] concertReservationId;
    private Long[] concertSeatIds;

    public static ConcertPaymentReq of(Long userId, Long[] concertReservationId, Long[] concertSeatIds) {
        ConcertPaymentReq request = new ConcertPaymentReq();
        request.userId = userId;
        request.concertReservationId = concertReservationId;
        request.concertSeatIds = concertSeatIds;
        return request;
    }
}
