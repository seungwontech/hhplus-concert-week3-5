package com.hhplus.tdd.concert.domain.repository;

import com.hhplus.tdd.concert.domain.model.ConcertSchedule;

import java.util.List;

public interface ConcertScheduleRepository {

    List<ConcertSchedule> getConcertSchedules(Long concertId);
}
