package com.hhplus.tdd.concert.infra.mapper;

import com.hhplus.tdd.concert.domain.model.Outbox;
import com.hhplus.tdd.concert.infra.entity.OutboxJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class OutboxMapper {

    public Outbox toDomain(OutboxJpaEntity entity) {
        return Outbox.builder()
                .id(entity.getId())
                .payload(entity.getPayload())
                .topic(entity.getTopic())
                .createdAt(entity.getCreatedAt())
                .status(entity.getStatus())
                .eventId(entity.getEventId())
                .build();
    }

    public OutboxJpaEntity toEntity(Outbox outbox) {
        return OutboxJpaEntity.builder()
                .id(outbox.getId())
                .payload(outbox.getPayload())
                .topic(outbox.getTopic())
                .status(outbox.getStatus())
                .eventId(outbox.getEventId())
                .build();
    }
}
