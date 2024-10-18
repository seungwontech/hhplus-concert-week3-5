package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.ConcertReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertReservationJpaRepository extends JpaRepository<ConcertReservationJpaEntity, Long> {
}
