package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.ConcertPaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertPaymentJpaRepository extends JpaRepository<ConcertPaymentJpaEntity, Long> {
    List<ConcertPaymentJpaEntity> findByUserIdAndConcertReservationIdIn(Long userId, Long[] concertReservationId);
}
