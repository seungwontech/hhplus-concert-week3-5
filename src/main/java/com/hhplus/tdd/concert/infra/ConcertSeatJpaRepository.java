package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.ConcertSeatJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatJpaEntity, Long> {
    List<ConcertSeatJpaEntity> findByConcertId(Long concertId);
    List<ConcertSeatJpaEntity> findByConcertIdAndConcertScheduleId(Long concertId, Long concertScheduleId);

    List<ConcertSeatJpaEntity> findByConcertIdAndConcerScheduleIdAndConcertSeatIdIn(Long concertId, Long concertScheduleId, Long[] concertSeatIds);

    int findPriceByReservationId(Long reservationId);

    List<ConcertSeatJpaEntity> findByConcertSeatIdIn(Long[] concertSeatIds);
}
