package com.hhplus.tdd.concert.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.tdd.concert.domain.event.MessageProducer;
import com.hhplus.tdd.concert.domain.model.ConcertEvent;
import com.hhplus.tdd.concert.domain.model.Outbox;
import com.hhplus.tdd.concert.domain.service.OutboxService;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertEventListener {

    private final WaitingQueueRepository waitingQueueRepository;

    private final OutboxService outboxService;
    private final MessageProducer messageProducer;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void expireWaitingQueue(ConcertEvent event) {
        if (!"COMPLETED".equals(event.getType())) {
            return;
        }

        String token = String.valueOf(event.getData().get("token"));
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueToken(token);
        waitingQueue.expire();
        waitingQueueRepository.save(waitingQueue);
        log.info("Waiting queue expired for token: {}", token);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(ConcertEvent event) throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(event);
        Outbox outbox = Outbox.builder()
                .payload(payload)
                .topic("payment_completed_topic")
                .status("WAITING")
                .eventId(event.getEventId())
                .build();
        outboxService.save(outbox);
        log.info("outBox save");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void sendkafka(ConcertEvent event) {
        messageProducer.send(event, event.getEventId());
        log.info("send kafka ");
    }
}
