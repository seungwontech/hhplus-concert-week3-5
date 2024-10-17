package com.hhplus.tdd.concert.presentation.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConcertReservationRes {
    private Long userId;
    private String concertName;
    private List<seat> seats;
    private int totalPrice;

    @Builder
    public static class seat {
        private int seatNumber;
        private String status;
        private int seatPrice;
    }
}
