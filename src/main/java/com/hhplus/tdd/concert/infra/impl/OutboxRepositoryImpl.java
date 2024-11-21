package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.Outbox;
import com.hhplus.tdd.concert.domain.repository.OutboxRepository;
import com.hhplus.tdd.concert.infra.OutboxJpaRepository;
import com.hhplus.tdd.concert.infra.entity.OutboxJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.OutboxMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
@Slf4j
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJpaRepository outboxJpaRepository;

    private final OutboxMapper outboxMapper;

    @Override
    public List<Outbox> getTopicAndStatus(String topic, String status) {
        List<OutboxJpaEntity> entities = outboxJpaRepository.findByTopicAndStatus(topic, status);
        if (entities.isEmpty()) {
            return null;
        }
        return entities.stream()
                .map(outboxMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Outbox outbox) {
        OutboxJpaEntity entity = outboxMapper.toEntity(outbox);
        outboxJpaRepository.save(entity);
    }

    @Override
    public Outbox getTopicAndEventId(String topic, String eventId) {
        OutboxJpaEntity entity = outboxJpaRepository.findByTopicAndEventId(topic, eventId);

        return outboxMapper.toDomain(entity);
    }
}
