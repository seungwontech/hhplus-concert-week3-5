package com.hhplus.tdd.concert.presentation.request;

import lombok.Getter;

@Getter
public class ConcertPaymentReq {
    private Long userId;
    private Long[] concertReservationId;
}
