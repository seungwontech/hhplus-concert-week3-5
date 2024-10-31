package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.infra.ConcertSeatJpaRepository;
import com.hhplus.tdd.concert.infra.entity.ConcertSeatJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.ConcertSeatMapper;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {

    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    private final ConcertSeatMapper concertSeatMapper;

    @Override
    public List<ConcertSeat> getConcertSeats(Long concertId) {
        List<ConcertSeatJpaEntity> entities = concertSeatJpaRepository.findByConcertId(concertId);
        if (entities.isEmpty()) {
            return null;
        }
        return entities.stream()
                .map(concertSeatMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertSeat> getConcertSeatsByScheduleOrThrow(Long concertId, Long concertScheduleId) {
        List<ConcertSeatJpaEntity> entities = concertSeatJpaRepository.findByConcertIdAndConcertScheduleId(concertId, concertScheduleId);
        if (entities.isEmpty()) {
            throw new CoreException(ErrorType.CONCERT_SEAT_NOT_FOUND, entities);
        }

        return entities.stream()
                .map(concertSeatMapper::toDomain)
                .collect(Collectors.toList());
    }


    @Override
    public List<ConcertSeat> findByConcertIdAndConcertScheduleIdAndConcertSeatIdIn(Long concertId, Long concertScheduleId, Long[] concertSeatIds) {
        List<ConcertSeatJpaEntity> entities = concertSeatJpaRepository.findByConcertIdAndConcertScheduleIdAndConcertSeatIdIn(concertId, concertScheduleId, concertSeatIds);
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

    @Override
    public List<ConcertSeat> getConcertSeatIdIn(Long[] concertSeatIds) {
        List<ConcertSeatJpaEntity> entities = concertSeatJpaRepository.findByConcertSeatIdIn(concertSeatIds);

        if (entities.isEmpty()) {
            return null;
        }

        return entities.stream()
                .map(concertSeatMapper::toDomain)
                .collect(Collectors.toList());
    }

}
