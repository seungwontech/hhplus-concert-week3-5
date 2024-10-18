package com.hhplus.tdd.concert.infra.mapper;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.infra.entity.ConcertJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ConcertMapper {
    // ConcertJpaEntity를 Concert 도메인 객체로 변환
    public Concert toDomain(ConcertJpaEntity entity) {
        return Concert.of(
                entity.getConcertId(),
                entity.getConcertTitle()
        );
    }

    public ConcertJpaEntity toEntity(Concert concert) {
        return ConcertJpaEntity.of(
                concert.getConcertId(),
                concert.getConcertTitle()
        );
    }
}
