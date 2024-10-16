package com.hhplus.tdd.app.concert.adapter.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
public class ScheduleRes {
    private long concertId;
    private List<schedule> schedules;

    @Builder
    public static class schedule {
        private long concertDateId;
        private LocalDateTime scheduleAt;
    }
}
