package com.hhplus.tdd.concert.presentation.request;

import lombok.Getter;

@Getter
public class ConcertReservationReq {
    private Long userId;
    private Long[] seatIds;
}
