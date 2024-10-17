package com.hhplus.tdd.concert.infra.mapper;

import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.infra.entity.ConcertScheduleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ConcertScheduleMapper {
    public ConcertSchedule toDomain(ConcertScheduleJpaEntity entity) {
        return ConcertSchedule.of(
                entity.getConcertScheduleId()
                , entity.getConcertId()
                , entity.getConcertDate()
                , entity.getTotalSeats()
        );
    }

    public ConcertScheduleJpaEntity toEntity(ConcertSchedule concertSchedule) {
        return ConcertScheduleJpaEntity.of(
                concertSchedule.getConcertScheduleId()
                , concertSchedule.getConcertId()
                , concertSchedule.getConcertDate()
                , concertSchedule.getTotalSeats()
        );
    }
}
