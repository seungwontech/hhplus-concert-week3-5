package com.hhplus.tdd.app.concert.adapter.request;

import lombok.Getter;

@Getter
public class ConcertPaymentReq {
    private long userId;
    private long[] concert_reservation_id;
}
