package com.hhplus.tdd.concert.presentation.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SeatRes {
    private Long concertId;
    private Long concertScheduleId;
    private List<seat> seats;

    @Builder
    public static class seat {
        private Long concertSeatId;
        private int seatNumber;
        private int seatPrice;
        private String reserveYn;
    }
}
