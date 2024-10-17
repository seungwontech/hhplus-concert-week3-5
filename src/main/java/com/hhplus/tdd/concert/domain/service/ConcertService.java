package com.hhplus.tdd.concert.domain.service;

import com.hhplus.tdd.concert.exception.ConcertErrorResult;
import com.hhplus.tdd.concert.exception.ConcertException;
import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;

    // 콘서트 조회
    public Concert getConcert(Long concertId) {
        Concert result = concertRepository.getConcert(concertId);
        if (result == null) {
            throw new ConcertException(ConcertErrorResult.CONCERT_NOT_FOUND);
        }
        return result;
    }

    // 콘서트 날짜 조회
    public List<ConcertSchedule> getConcertSchedules(Long concertId) {
        List<ConcertSchedule> schedules = concertScheduleRepository.getConcertSchedules(concertId);
        if (schedules.isEmpty()) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SCHEDULE_NOT_FOUND);
        }
        return schedules;
    }

    // 콘서트 좌석 조회
    public List<ConcertSeat> getConcertSeats(Long concertId) {
        List<ConcertSeat> seats = concertSeatRepository.getConcertSeats(concertId);
        if (seats.isEmpty()) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SEAT_NOT_FOUND);
        }
        return seats;
    }


    public ConcertSchedule getConcertSchedule(Long concertId, Long concertScheduleId) {
        ConcertSchedule schedule = concertScheduleRepository.getConcertSchedule(concertId, concertScheduleId);
        if (schedule == null) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SCHEDULE_NOT_FOUND);
        }
        return schedule;
    }


    public List<ConcertSeat> getConcertSeatsBySchedule(Long concertId, Long concertScheduleId) {
        List<ConcertSeat> seats = concertSeatRepository.getConcertSeatsBySchedule(concertId, concertScheduleId);
        if (seats == null) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SEAT_NOT_FOUND);
        }
        return seats;
    }
}
