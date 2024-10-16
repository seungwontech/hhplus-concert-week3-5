package com.hhplus.tdd.app.concert.adapter.request;

import lombok.Getter;

@Getter
public class ConcertReservationReq {
    private long userId;
    private long[] seatIds;
}
