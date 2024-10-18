package com.hhplus.tdd.concert.presentation.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ConcertReservationRes {
    private Long userId;
    private String concertName;
    private List<Seat> seats;
    private int totalPrice;

    private ConcertReservationRes(Long userId, String concertName, List<Seat> seats, int totalPrice) {
        this.userId = userId;
        this.concertName = concertName;
        this.seats = seats;
        this.totalPrice = totalPrice;
    }

    public static ConcertReservationRes of(Long userId, String concertName, List<Seat> seats, int totalPrice) {
        return new ConcertReservationRes(userId, concertName, seats, totalPrice);
    }

    @Getter
    public static class Seat {
        private int seatNumber;
        private String status;
        private int seatPrice;

        private Seat(int seatNumber, String status, int seatPrice) {
            this.seatNumber = seatNumber;
            this.status = status;
            this.seatPrice = seatPrice;
        }

        public static Seat of(int seatNumber, String status, int seatPrice) {
            return new Seat(seatNumber, status, seatPrice);
        }
    }
}
