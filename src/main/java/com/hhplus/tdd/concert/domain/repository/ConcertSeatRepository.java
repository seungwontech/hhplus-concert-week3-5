package com.hhplus.tdd.concert.domain.repository;

import com.hhplus.tdd.concert.domain.model.ConcertSeat;

import java.util.List;

public interface ConcertSeatRepository {

    List<ConcertSeat> getConcertSeats(Long concertId);

    List<ConcertSeat> getConcertSeatsBySchedule(Long concertId, Long concertScheduleId);
}
