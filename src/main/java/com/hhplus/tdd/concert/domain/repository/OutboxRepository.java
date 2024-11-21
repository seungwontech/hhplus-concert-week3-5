package com.hhplus.tdd.concert.domain.repository;

import com.hhplus.tdd.concert.domain.model.Outbox;

import java.util.List;

public interface OutboxRepository {
    List<Outbox> getTopicAndStatus(String status);

    void save(Outbox outbox);

    Outbox getTopicAndEventId(String topic, String eventId);
}
