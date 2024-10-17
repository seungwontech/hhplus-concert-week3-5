package com.hhplus.tdd.waitingqueue.infra.mapper;

import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.infra.entity.WaitingQueueJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class WaitingQueueMapper {
    public WaitingQueue toDomain(WaitingQueueJpaEntity entity) {
        return WaitingQueue.of(
                entity.getQueueId(),
                entity.getUserId(),
                entity.getToken(),
                entity.getTokenExpiry(),
                entity.getTokenCreated(),
                entity.getTokenStatus()
        );
    }

    public WaitingQueueJpaEntity toEntity(WaitingQueue domain) {
        return WaitingQueueJpaEntity.of(
                domain.getQueueId(),
                domain.getUserId(),
                domain.getToken(),
                domain.getTokenExpiry(),
                domain.getTokenCreated(),
                domain.getTokenStatus()
        );
    }

}
