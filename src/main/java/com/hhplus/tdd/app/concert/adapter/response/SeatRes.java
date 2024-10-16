package com.hhplus.tdd.app.concert.adapter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SeatRes {
    private long concertId;
    private long scheduleId;
    private List<seat> seats;

    @Builder
    public static class seat {
        private long seatId;
        private int seatNumber;
        private String reserveYn;
    }
}
