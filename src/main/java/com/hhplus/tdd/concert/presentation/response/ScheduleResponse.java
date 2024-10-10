package com.hhplus.tdd.concert.presentation.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class ScheduleResponse {
    private Long concertId;
    private List<concertSchedule> concertSchedules;


    @Getter
    @Builder
    public static class concertSchedule {
        private Long concertDateId;
        private LocalDateTime scheduleAt;
    }
}
