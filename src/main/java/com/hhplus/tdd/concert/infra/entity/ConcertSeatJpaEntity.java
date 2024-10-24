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
    @Column(name = "concert_seat_id")
    private Long concertSeatId; // 콘서트 좌석 ID

    @Column(name = "concert_schedule_id", nullable = false)
    private Long concertScheduleId; // 콘서트 일정 ID

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber; // 좌석 번호

    @Column(name = "seat_price", nullable = false)
    private int seatPrice; // 좌석 가격

    @Column(name = "reserve_yn", nullable = false)
    private String reserveYn; // 예약 여부 (Y/N)

    @Version
    @Column(nullable = false)
    private Long version = 1L;

    public static ConcertSeatJpaEntity of(Long concertSeatId, Long concertScheduleId, Long concertId, int seatNumber, int seatPrice, String reserveYn, Long version) {
        return new ConcertSeatJpaEntity(concertSeatId, concertScheduleId, concertId, seatNumber, seatPrice, reserveYn, version);
    }
}