package com.hhplus.tdd.concert.domain.service;

import com.hhplus.tdd.concert.domain.model.Outbox;
import com.hhplus.tdd.concert.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class OutboxService {

    private final OutboxRepository outboxRepository;

    @Transactional
    public void saveSendOutbox(String topic, String eventId) {
        Outbox outbox = outboxRepository.getTopicAndEventId(topic, eventId);
        outbox.sent();
        outboxRepository.save(outbox);
    }

    @Transactional
    public void save(Outbox Outbox) {
        outboxRepository.save(Outbox);
    }

}
