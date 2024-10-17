package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.infra.ConcertJpaRepository;
import com.hhplus.tdd.concert.infra.ConcertSeatJpaRepository;
import com.hhplus.tdd.concert.infra.entity.ConcertScheduleJpaEntity;
import com.hhplus.tdd.concert.infra.entity.ConcertSeatJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.ConcertMapper;
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
        // 엔티티 리스트를 도메인 리스트로 변환
        return entities.stream()
                .map(concertSeatMapper::toDomain)
                .collect(Collectors.toList());
    }
}
