package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.ConcertSeatJpaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatJpaEntity, Long> {
    List<ConcertSeatJpaEntity> findByConcertId(Long concertId);

    @Lock(LockModeType.OPTIMISTIC)
    List<ConcertSeatJpaEntity> findByConcertIdAndConcertScheduleIdAndConcertSeatIdIn(Long concertId, Long concertScheduleId, Long[] concertSeatIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ConcertSeatJpaEntity> findByConcertSeatIdIn(Long[] concertSeatIds);

    List<ConcertSeatJpaEntity> findByConcertIdAndConcertScheduleIdAndReserveYn(Long concertId, Long concertScheduleId, String reserveYn);
}
