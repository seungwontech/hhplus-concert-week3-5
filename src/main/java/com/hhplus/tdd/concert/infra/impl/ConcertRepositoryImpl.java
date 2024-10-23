package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.infra.ConcertJpaRepository;
import com.hhplus.tdd.concert.infra.entity.ConcertJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.ConcertMapper;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    private final ConcertMapper concertMapper;

    @Override
    public Concert getConcertOrThrow(Long concertId) {

        return concertJpaRepository.findById(concertId)
                .map(concertMapper::toDomain).orElseThrow(() -> new CoreException(ErrorType.CONCERT_NOT_FOUND, concertId));
    }

    @Override
    public void save(Concert concert) {
        ConcertJpaEntity entity = concertMapper.toEntity(concert);
        concertJpaRepository.save(entity);
    }
}
