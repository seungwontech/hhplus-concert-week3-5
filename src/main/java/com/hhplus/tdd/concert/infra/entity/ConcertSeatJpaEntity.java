package com.hhplus.tdd.concert.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "CONCERT_SEAT")
public class ConcertSeatJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertSeatId; // 콘서트 좌석 ID

    @Column(nullable = false)
    private Long concertScheduleId; // 콘서트 일정 ID

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private int seatNumber; // 좌석 번호

    @Column(nullable = false)
    private int seatPrice; // 좌석 가격

    @Column(nullable = false)
    private String reserveYn; // 예약 여부 (Y/N)

    public static ConcertSeatJpaEntity of(Long concertSeatId, Long concertScheduleId, Long concertId, int seatNumber, int seatPrice, String reserveYn) {
        return new ConcertSeatJpaEntity(concertSeatId, concertScheduleId, concertId, seatNumber, seatPrice, reserveYn);
    }
}