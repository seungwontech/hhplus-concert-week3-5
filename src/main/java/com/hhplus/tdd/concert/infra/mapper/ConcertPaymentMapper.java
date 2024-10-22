package com.hhplus.tdd.concert.infra.mapper;

import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.infra.entity.ConcertPaymentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ConcertPaymentMapper {

    public ConcertPaymentJpaEntity toEntity(ConcertPayment concertPayment) {
        return ConcertPaymentJpaEntity.of(
                concertPayment.getUserId(),
                concertPayment.getConcertReservationId(),
                concertPayment.getPaymentAmount(),
                concertPayment.getPaymentStatus(),
                concertPayment.getPaymentDate()
        );
    }

    public ConcertPayment toDomain(ConcertPaymentJpaEntity concertPaymentJpaEntity) {
        return ConcertPayment.of(
                concertPaymentJpaEntity.getPaymentId(),
                concertPaymentJpaEntity.getUserId(),
                concertPaymentJpaEntity.getConcertReservationId(),
                concertPaymentJpaEntity.getPaymentAmount(),
                concertPaymentJpaEntity.getPaymentStatus(),
                concertPaymentJpaEntity.getPaymentDate()
        );
    }
}
