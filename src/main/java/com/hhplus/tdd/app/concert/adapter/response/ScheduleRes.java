package com.hhplus.tdd.app.concert.adapter.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
public class ScheduleRes {
    private Long concertId;
    private List<schedule> schedules;

    @Builder
    public static class schedule {
        private Long concertDateId;
        private LocalDateTime scheduleAt;
    }
}
