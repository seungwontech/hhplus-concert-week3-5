package com.hhplus.tdd.concert.presentation.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ConcertReservationRes {
    private Long userId;
    private String concertName;
    private List<Seat> seats;
    private int totalPrice;
    private Long concertReservationId;

    public static ConcertReservationRes of(Long userId, String concertName, List<Seat> seats, int totalPrice, Long concertReservationId) {
        return new ConcertReservationRes(userId, concertName, seats, totalPrice, concertReservationId);
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
