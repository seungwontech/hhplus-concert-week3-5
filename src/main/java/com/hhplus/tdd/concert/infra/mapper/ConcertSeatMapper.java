package com.hhplus.tdd.concert.infra.mapper;

import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.infra.entity.ConcertSeatJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ConcertSeatMapper {

    public ConcertSeat toDomain(ConcertSeatJpaEntity entity) {
        return ConcertSeat.of(
                entity.getConcertSeatId(),
                entity.getConcertScheduleId(),
                entity.getConcertId(),
                entity.getSeatNumber(),
                entity.getSeatPrice(),
                entity.getReserveYn(),
                entity.getVersion()
        );
    }

    public ConcertSeatJpaEntity toEntity(ConcertSeat concertSeat) {
        return ConcertSeatJpaEntity.of(
                concertSeat.getConcertSeatId(),
                concertSeat.getConcertScheduleId(),
                concertSeat.getConcertId(),
                concertSeat.getSeatNumber(),
                concertSeat.getSeatPrice(),
                concertSeat.getReserveYn(),
                concertSeat.getVersion()
        );
    }
}
