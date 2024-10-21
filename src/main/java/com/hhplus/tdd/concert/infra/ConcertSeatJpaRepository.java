package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.ConcertSeatJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatJpaEntity, Long> {
    List<ConcertSeatJpaEntity> findByConcertId(Long concertId);

    List<ConcertSeatJpaEntity> findByConcertIdAndConcertScheduleIdAndConcertSeatIdIn(Long concertId, Long concertScheduleId, Long[] concertSeatIds);

    List<ConcertSeatJpaEntity> findByConcertSeatIdIn(Long[] concertSeatIds);

    List<ConcertSeatJpaEntity> findByConcertIdAndConcertScheduleIdAndReserveYn(Long concertId, Long concertScheduleId, String reserveYn);
}
