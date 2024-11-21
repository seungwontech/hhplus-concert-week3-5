package com.hhplus.tdd.concert.consumer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.tdd.concert.domain.model.ConcertEvent;
import com.hhplus.tdd.concert.domain.service.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageConsumer {

    private final OutboxService outboxService;
    private final ObjectMapper objectMapper;

    final String TOPIC = "payment_completed_topic";

    @KafkaListener(topics = TOPIC, groupId = "send_email")
    public void sendEmailConsume(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("send Email Consume start");
        var concertEvent = objectMapper.readValue(record.value(), ConcertEvent.class);

        log.info("Received type {}", concertEvent);

        if (!"COMPLETED".equals(concertEvent.getType())) {
            log.info("이메일 보내자 : {}", concertEvent.getData());
        }
    }

    @KafkaListener(topics = TOPIC, groupId = "outbox_save")
    public void outboxConsume(ConsumerRecord<String, String> record){
        log.info("outbox consume start : {}" ,record.key());
        outboxService.saveSendOutbox(TOPIC, record.key());
    }
}