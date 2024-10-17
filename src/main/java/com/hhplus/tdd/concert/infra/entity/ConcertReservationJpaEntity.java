package com.hhplus.tdd.concert.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "CONCERT_RESERVATION")
public class ConcertReservationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_reservation_id")
    private Long concertReservationId; // 콘서트 예약 ID

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 ID

    @Column(name = "concert_schedule_id", nullable = false)
    private Long concertScheduleId; // 콘서트 일정 ID

    @Column(name = "concert_seat_id", nullable = false)
    private Long concertSeatId; // 콘서트 좌석 ID

    @Column(name = "reservation_status", nullable = false)
    private String reservationStatus; // 예약 상태

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate; // 예약 날짜

    @Column(name = "reservation_expiry", nullable = false)
    private LocalDateTime reservationExpiry; // 예약 만료 시간

    public static ConcertReservationJpaEntity of(Long concertReservationId, Long userId, Long concertScheduleId, Long concertSeatId, String reservationStatus, LocalDateTime reservationDate, LocalDateTime reservationExpiry) {
        return new ConcertReservationJpaEntity(concertReservationId, userId, concertScheduleId, concertSeatId, reservationStatus, reservationDate, reservationExpiry);
    }
}
