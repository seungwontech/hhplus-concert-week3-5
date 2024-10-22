package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.ConcertScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleJpaEntity, Long> {

    List<ConcertScheduleJpaEntity> findByConcertId(Long concertId);

    ConcertScheduleJpaEntity findByConcertIdAndConcertScheduleId(Long concertId, Long concertScheduleId);

}
