package com.hhplus.tdd.concert.infra;


import com.hhplus.tdd.concert.infra.entity.OutboxJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<OutboxJpaEntity, Long> {

    List<OutboxJpaEntity> findByTopicAndStatus(String topic, String status);

    OutboxJpaEntity findByTopicAndEventId(String topic, String eventId);
}
