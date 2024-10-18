package com.hhplus.tdd.concert.infra.mapper;

import com.hhplus.tdd.concert.domain.model.ConcertReservation;
import com.hhplus.tdd.concert.infra.entity.ConcertReservationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ConcertReservationMapper {
    public ConcertReservation toDomain(ConcertReservationJpaEntity entity) {
        return ConcertReservation.of(
                entity.getConcertReservationId(),
                entity.getUserId(),
                entity.getConcertScheduleId(), // 콘서트 일정 ID
                entity.getConcertSeatId(), // 콘서트 좌석 ID
                entity.getReservationStatus(), // 예약 상태
                entity.getReservationDate(), // 예약 날짜
                entity.getReservationExpiry()
        );
    }

    public ConcertReservationJpaEntity toEntity(ConcertReservation domain) {
        return ConcertReservationJpaEntity.of(
                domain.getConcertReservationId(),
                domain.getUserId(),
                domain.getConcertScheduleId(),
                domain.getConcertSeatId(),
                domain.getReservationStatus(),
                domain.getReservationDate(),
                domain.getReservationExpiry()
        );
    }
}
