package com.hhplus.tdd.concert.presentation.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleRes {
    private Long concertId;
    private List<Schedule> schedules;

    public static ScheduleRes of(Long concertId, List<Schedule> schedules) {
        return new ScheduleRes(concertId, schedules);
    }

    private ScheduleRes(Long concertId, List<Schedule> schedules) {
        this.concertId = concertId;
        this.schedules = schedules;
    }

    @Getter
    public static class Schedule {
        private Long concertScheduleId;
        private LocalDateTime concertDate;
        private int totalSeats;

        public static Schedule of(Long concertScheduleId, LocalDateTime concertDate, int totalSeats) {
            return new Schedule(concertScheduleId, concertDate, totalSeats);
        }

        private Schedule(Long concertScheduleId, LocalDateTime concertDate, int totalSeats) {
            this.concertScheduleId = concertScheduleId;
            this.concertDate = concertDate;
            this.totalSeats = totalSeats;
        }
    }
}
