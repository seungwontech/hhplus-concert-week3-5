package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.infra.ConcertJpaRepository;
import com.hhplus.tdd.concert.infra.ConcertScheduleJpaRepository;
import com.hhplus.tdd.concert.infra.entity.ConcertScheduleJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.ConcertMapper;
import com.hhplus.tdd.concert.infra.mapper.ConcertScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

    private final ConcertScheduleMapper concertScheduleMapper;

    @Override
    public List<ConcertSchedule> getConcertSchedules(Long concertId) {
        List<ConcertScheduleJpaEntity> entities = concertScheduleJpaRepository.findByConcertId(concertId);

        return entities.stream()
                .map(concertScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ConcertSchedule getConcertSchedule(Long concertId, Long concertScheduleId) {
        ConcertScheduleJpaEntity entity = concertScheduleJpaRepository.findByConcertIdAndConcertScheduleId(concertId, concertScheduleId);

        return concertScheduleMapper.toDomain(entity);
    }

}
