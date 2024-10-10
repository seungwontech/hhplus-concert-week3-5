package com.hhplus.tdd.concert.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SeatReservationResponse {
    private Long userId;
    private String concertName;
    private List<seat> seats;
    private int totalPrice;


    @Getter
    @Builder
    public static class seat {
        private int seatNumber;
        private String status;
        private int seatPrice;
    }

}
