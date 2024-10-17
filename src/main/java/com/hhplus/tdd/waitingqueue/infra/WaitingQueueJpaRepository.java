package com.hhplus.tdd.waitingqueue.infra;


import com.hhplus.tdd.waitingqueue.infra.entity.WaitingQueueJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WaitingQueueJpaRepository extends JpaRepository<WaitingQueueJpaEntity, Long> {
    WaitingQueueJpaEntity findByUserId(Long userId);

    WaitingQueueJpaEntity findByToken(String token);

    @Query("SELECT w.queueId FROM WaitingQueueJpaEntity w WHERE w.tokenStatus = 'ACTIVE' ORDER BY w.tokenCreated DESC")
    Optional<Long> getLastActivePosition();
}
