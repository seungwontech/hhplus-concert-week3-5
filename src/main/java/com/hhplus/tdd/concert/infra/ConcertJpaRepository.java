package com.hhplus.tdd.concert.infra;

import com.hhplus.tdd.concert.infra.entity.ConcertJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConcertJpaRepository extends JpaRepository<ConcertJpaEntity, Long> {
}
