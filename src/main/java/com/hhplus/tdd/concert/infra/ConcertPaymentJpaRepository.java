package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertPaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
}
