package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.exception.ConcertErrorResult;
import com.hhplus.tdd.concert.exception.ConcertException;
import com.hhplus.tdd.concert.presentation.response.ScheduleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GetAvailableConcertDatesUseCase {

    private final ConcertService concertService;

    public ScheduleRes execute(Long concertId) {

        Concert concert = concertService.getConcert(concertId);

        List<ConcertSchedule> schedules = concertService.getConcertSchedules(concertId);

        List<ConcertSeat> seats = concertService.getConcertSeats(concertId);

        Map<Long, Integer> reservedSeatsMap = countReservedSeatsByScheduleId(seats);

        List<ScheduleRes.Schedule> availableSchedules = filterAvailableSchedules(schedules, reservedSeatsMap);

        return buildScheduleResponse(concert, availableSchedules);
    }

    private Map<Long, Integer> countReservedSeatsByScheduleId(List<ConcertSeat> seats) {
        Map<Long, Integer> reservedSeatsMap = new HashMap<>();
        for (ConcertSeat seat : seats) {
            if ("Y".equals(seat.getReserveYn())) {
                Long scheduleId = seat.getConcertScheduleId();
                reservedSeatsMap.put(scheduleId, reservedSeatsMap.getOrDefault(scheduleId, 0) + 1);
            }
        }
        return reservedSeatsMap;
    }

    private List<ScheduleRes.Schedule> filterAvailableSchedules(List<ConcertSchedule> schedules, Map<Long, Integer> reservedSeatsMap) {
        List<ScheduleRes.Schedule> scheduleList = new ArrayList<>();
        for (ConcertSchedule schedule : schedules) {
            int reservedSeats = reservedSeatsMap.getOrDefault(schedule.getConcertScheduleId(), 0);
            if (schedule.getTotalSeats() - reservedSeats > 0) {
                ScheduleRes.Schedule availableSchedule = ScheduleRes.Schedule.of(
                        schedule.getConcertScheduleId(),
                        schedule.getConcertDate(),
                        schedule.getTotalSeats()
                );
                scheduleList.add(availableSchedule);
            }
        }
        return scheduleList;
    }

    private ScheduleRes buildScheduleResponse(Concert concert, List<ScheduleRes.Schedule> scheduleList) {
        if (scheduleList.isEmpty()) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SCHEDULE_AVAILABLE_NOT_FOUND);
        } else {
            return ScheduleRes.of(concert.getConcertId(), scheduleList);
        }
    }
}
