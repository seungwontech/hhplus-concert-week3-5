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
@Table(name = "CONCERT_SCHEDULE")
public class ConcertScheduleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertScheduleId;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private LocalDateTime concertDate;

    @Column(nullable = false)
    private int totalSeats;

    public static ConcertScheduleJpaEntity of(Long concertScheduleId, Long concertId, LocalDateTime concertDate, int totalSeats) {
        return new ConcertScheduleJpaEntity(concertScheduleId, concertId, concertDate, totalSeats);
    }
}