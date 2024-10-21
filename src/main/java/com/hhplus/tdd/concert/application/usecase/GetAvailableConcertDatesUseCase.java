package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
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

    private final ConcertRepository concertRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;

    public ScheduleRes execute(Long concertId) {

        Concert concert = concertRepository.getConcert(concertId);
        List<ConcertSchedule> schedules = concertScheduleRepository.getConcertSchedules(concertId);
        List<ConcertSeat> seats = concertSeatRepository.getConcertSeats(concertId);

        if (concert == null) {
            throw new IllegalArgumentException("콘서트가 존재하지 않습니다.");
        }
        if (schedules.isEmpty()) {
            throw new IllegalArgumentException("날짜가 존재하지 않습니다.");
        }
        if (seats.isEmpty()) {
            throw new IllegalArgumentException("좌석이 존재하지 않습니다.");
        }

        Map<Long, Integer> reservedSeatsMap = countReservedSeatsByScheduleId(seats);

        List<ScheduleRes.Schedule> availableSchedules = filterAvailableSchedules(schedules, reservedSeatsMap);

        return buildScheduleResponse(concert, availableSchedules);
    }

    // 콘서트 일정별로 예약된 좌석 수를 계산하는 메서드.
    public Map<Long, Integer> countReservedSeatsByScheduleId(List<ConcertSeat> seats) {
        Map<Long, Integer> reservedSeatsMap = new HashMap<>();
        for (ConcertSeat seat : seats) {
            if ("Y".equals(seat.getReserveYn())) {
                Long scheduleId = seat.getConcertScheduleId();
                reservedSeatsMap.put(scheduleId, reservedSeatsMap.getOrDefault(scheduleId, 0) + 1);
            }
        }
        return reservedSeatsMap;
    }

    // 사용 가능한 콘서트 일정을 필터링하는 메서드.
    public List<ScheduleRes.Schedule> filterAvailableSchedules(List<ConcertSchedule> schedules, Map<Long, Integer> reservedSeatsMap) {
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

    public ScheduleRes buildScheduleResponse(Concert concert, List<ScheduleRes.Schedule> scheduleList) {
        if (scheduleList.isEmpty()) {
            throw new IllegalArgumentException("콘서트의 사용 가능한 일정 없습니다.");
        } else {
            return ScheduleRes.of(concert.getConcertId(), scheduleList);
        }
    }
}
