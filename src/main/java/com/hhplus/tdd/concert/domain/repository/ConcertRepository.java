package com.hhplus.tdd.concert.domain.repository;

import com.hhplus.tdd.concert.domain.model.Concert;

public interface ConcertRepository {
    Concert getConcertOrThrow(Long concertId);

    void save(Concert concert);
}
