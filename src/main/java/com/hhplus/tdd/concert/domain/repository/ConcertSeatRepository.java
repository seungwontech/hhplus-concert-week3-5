package com.hhplus.tdd.concert.domain.repository;

import com.hhplus.tdd.concert.domain.model.ConcertSeat;

import java.util.List;

public interface ConcertSeatRepository {

    List<ConcertSeat> getConcertSeats(Long concertId);

    List<ConcertSeat> findByConcertIdAndConcertScheduleIdAndConcertSeatIdIn(Long concertId, Long concertScheduleId, Long[] concertSeatIds);

    void saveAll(List<ConcertSeat> concertSeats);

    List<ConcertSeat> getConcertSeatsByScheduleOrThrow(Long concertId, Long concertScheduleId, String reserveYn);

    List<ConcertSeat> getConcertSeatIdIn(Long[] concertSeatIds);
}
