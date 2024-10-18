package com.hhplus.tdd.concert.domain.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ConcertSchedule {
    private Long concertScheduleId;   // 콘서트 일정 ID
    private Long concertId;           // 콘서트 ID
    private LocalDateTime concertDate; // 공연 날짜
    private int totalSeats;           // 총 좌석 수

    public static ConcertSchedule of(Long concertScheduleId, Long concertId, LocalDateTime concertDate, int totalSeats) {
        return new ConcertSchedule(concertScheduleId, concertId, concertDate, totalSeats);
    }
}