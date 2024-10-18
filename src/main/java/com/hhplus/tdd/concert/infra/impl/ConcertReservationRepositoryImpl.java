package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.ConcertReservation;
import com.hhplus.tdd.concert.domain.repository.ConcertReservationRepository;
import com.hhplus.tdd.concert.infra.ConcertReservationJpaRepository;
import com.hhplus.tdd.concert.infra.entity.ConcertReservationJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.ConcertReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ConcertReservationRepositoryImpl implements ConcertReservationRepository {

    private final ConcertReservationJpaRepository concertReservationJpaRepository;
    private final ConcertReservationMapper concertReservationMapper;

    @Override
    public void saveAll(List<ConcertReservation> reservations) {
        List<ConcertReservationJpaEntity> entities = reservations.stream()
                .map(concertReservationMapper::toEntity)
                .toList();
        concertReservationJpaRepository.saveAll(entities);
    }

    @Override
    public List<ConcertReservation> findAll() {
        List<ConcertReservationJpaEntity> entities =  concertReservationJpaRepository.findAll();
        return entities.stream()
                .map(concertReservationMapper::toDomain)
                .collect(Collectors.toList());
    }

}
