package com.hhplus.tdd.concert.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ConcertReservation {
    private Long concertReservationId; // 콘서트 예약 ID
    private Long userId;               // 사용자 ID
    private Long concertScheduleId;    // 콘서트 일정 ID
    private Long concertSeatId;        // 콘서트 좌석 ID
    private String reservationStatus;  // 예약 상태 (예약완료, 예약대기, 예약취소)
    private LocalDateTime reservationDate; // 예약 날짜
    private LocalDateTime reservationExpiry; // 예약 날짜

    public static ConcertReservation of(Long concertReservationId, Long userId, Long concertScheduleId, Long concertSeatId, String reservationStatus, LocalDateTime reservationDate, LocalDateTime reservationExpiry) {
        return new ConcertReservation(concertReservationId, userId, concertScheduleId, concertSeatId, reservationStatus, reservationDate, reservationExpiry);
    }

}
