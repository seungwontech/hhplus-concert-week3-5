package com.hhplus.tdd.concert.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ConcertReservationResult {
    private Long userId;
    private String concertName;
    private List<com.hhplus.tdd.concert.presentation.response.ConcertReservationRes.Seat> seats;
    private int totalPrice;


    public static ConcertReservationResult of(Long userId, String concertName, List<com.hhplus.tdd.concert.presentation.response.ConcertReservationRes.Seat> seats, int totalPrice) {
        return new ConcertReservationResult(userId, concertName, seats, totalPrice);
    }

    @AllArgsConstructor
    @Getter
    public static class Seat {
        private int seatNumber;
        private String status;
        private int seatPrice;

        public static Seat of(int seatNumber, String status, int seatPrice) {
            return new Seat(seatNumber, status, seatPrice);
        }
    }
}
