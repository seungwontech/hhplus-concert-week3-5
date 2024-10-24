package com.hhplus.tdd.concert.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ConcertSeat {
    private Long concertSeatId;        // 콘서트 좌석 ID
    private Long concertScheduleId;    // 콘서트 일정 ID
    private Long concertId;
    private int seatNumber;            // 좌석 번호
    private int seatPrice;             // 좌석 가격
    private String reserveYn;          // 예약 여부 (Y/N)

    public static ConcertSeat of(Long concertSeatId, Long concertScheduleId, Long concertId, int seatNumber, int seatPrice, String reserveYn) {
        return new ConcertSeat(concertSeatId, concertScheduleId, concertId, seatNumber, seatPrice, reserveYn);
    }


    public void setAsReserved() {
        this.reserveYn = "Y";
    }
}