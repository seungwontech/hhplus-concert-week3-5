package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.infra.ConcertSeatJpaRepository;
import com.hhplus.tdd.concert.infra.entity.ConcertSeatJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.ConcertSeatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {

    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    private final ConcertSeatMapper concertSeatMapper;

    @Override
    public List<ConcertSeat> getConcertSeats(Long concertId) {
        List<ConcertSeatJpaEntity> entities = concertSeatJpaRepository.findByConcertId(concertId);
        return entities.stream()
                .map(concertSeatMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertSeat> getConcertSeatsBySchedule(Long concertId, Long concertScheduleId) {
        List<ConcertSeatJpaEntity> entities = concertSeatJpaRepository.findByConcertIdAndConcertScheduleId(concertId, concertScheduleId);
        return entities.stream()
                .map(concertSeatMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertSeat> findByConcertIdAndConcertScheduleIdAndSeatIdIn(Long concertId, Long concertScheduleId, Long[] concertSeatIds) {
        List<ConcertSeatJpaEntity> entities = concertSeatJpaRepository.findByConcertIdAndConcerScheduleIdAndConcertSeatIdIn(concertId, concertScheduleId, concertSeatIds);
        return entities.stream()
                .map(concertSeatMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<ConcertSeat> concertSeats) {
        List<ConcertSeatJpaEntity> entities = concertSeats.stream()
                .map(concertSeatMapper::toEntity)
                .toList();
        concertSeatJpaRepository.saveAll(entities);
    }
}
