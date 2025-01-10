package com.hhplus.tdd.concert.infra.mapper;

import com.hhplus.tdd.concert.domain.model.Outbox;
import com.hhplus.tdd.concert.infra.entity.OutboxJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class OutboxMapper {

    public Outbox toDomain(OutboxJpaEntity entity) {
        return Outbox.of(
                entity.getId()
                , entity.getPayload()
                , entity.getTopic()
                , entity.getCreatedAt()
                , entity.getStatus()
                , entity.getEventId()
        );
    }

    public OutboxJpaEntity toEntity(Outbox outbox) {
        return OutboxJpaEntity.of(outbox.getId()
                , outbox.getPayload()
                , outbox.getTopic()
                , outbox.getCreatedAt()
                , outbox.getStatus()
                , outbox.getEventId()
        );
    }
}
