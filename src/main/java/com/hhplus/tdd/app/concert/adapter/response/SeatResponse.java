package com.hhplus.tdd.app.concert.adapter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SeatResponse {
    private Long concertId;
    private Long concertDateId;
    private List<concertSeat> concertSeats;

    @Getter
    @Builder
    public static class concertSeat {
        private Long seatId;
        private int seatNumber;
        private String reserveYn;
    }
}
