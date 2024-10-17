package com.hhplus.tdd.concert.presentation.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SeatRes {
    private Long concertId;
    private Long concertScheduleId;
    private LocalDateTime concertDate;
    private List<Seat> seats;

    public static SeatRes of(Long concertId, Long concertScheduleId, LocalDateTime concertDate, List<Seat> seats) {
        SeatRes seatRes = new SeatRes();
        seatRes.concertId = concertId;
        seatRes.concertScheduleId = concertScheduleId;
        seatRes.concertDate = concertDate;
        seatRes.seats = seats;
        return seatRes;
    }

    @Getter
    public static class Seat {
        private Long concertSeatId;
        private int seatNumber;
        private int seatPrice;
        private String reserveYn;

        public static Seat of(Long concertSeatId, int seatNumber, int seatPrice, String reserveYn) {
            Seat seat = new Seat();
            seat.concertSeatId = concertSeatId;
            seat.seatNumber = seatNumber;
            seat.seatPrice = seatPrice;
            seat.reserveYn = reserveYn;
            return seat;
        }
    }
}