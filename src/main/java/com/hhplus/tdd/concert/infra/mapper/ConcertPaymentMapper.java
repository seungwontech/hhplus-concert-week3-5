package com.hhplus.tdd.concert.infra.mapper;

import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.infra.entity.ConcertPaymentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ConcertPaymentMapper {

    public ConcertPaymentJpaEntity toEntity(ConcertPayment concertPayment) {
        return null;
    }
}
