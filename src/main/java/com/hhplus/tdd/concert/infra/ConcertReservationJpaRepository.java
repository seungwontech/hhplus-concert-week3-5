package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.ConcertReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertReservationJpaRepository extends JpaRepository<ConcertReservationJpaEntity, Long> {
    List<ConcertReservationJpaEntity> findByConcertReservationIdIn(Long[] concertReservationId);
}
