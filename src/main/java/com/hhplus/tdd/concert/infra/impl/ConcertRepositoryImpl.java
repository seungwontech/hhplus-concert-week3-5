package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.infra.ConcertJpaRepository;
import com.hhplus.tdd.concert.infra.mapper.ConcertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    private final ConcertMapper concertMapper;

    @Override
    public Concert getConcert(Long concertId) {

        return concertJpaRepository.findById(concertId)
                .map(concertMapper::toDomain).orElse(null);
    }
}
