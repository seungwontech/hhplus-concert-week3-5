package com.hhplus.tdd.concert.scheduler;

import com.hhplus.tdd.concert.domain.model.ConcertEvent;
import com.hhplus.tdd.concert.domain.model.Outbox;
import com.hhplus.tdd.concert.domain.service.OutboxService;
import com.hhplus.tdd.concert.infra.kafka.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertScheduler {

    private final OutboxService outboxService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @Scheduled(fixedRate = 60000)
    public void retryScheduler() {
        log.info("outbox 대기중인 이벤트 발송 스케줄러 실행");

        List<Outbox> outboxes = outboxService.getTopicAndStatus("WAITING");

        if (outboxes != null) {
            for (int i = 0; i < outboxes.size(); i++) {
                ConcertEvent event = ConcertEvent.toRetryCompleteEvent(outboxes.get(i).getEventId(), outboxes.get(i));
                kafkaMessageProducer.send(event, outboxes.get(i).getEventId());
            }
        }
    }
}
