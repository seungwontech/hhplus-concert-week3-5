package com.hhplus.tdd.concert.infra.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.tdd.concert.domain.model.ConcertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void send(ConcertEvent event, String eventId) {
        try {
            String jsonObject = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("payment_completed_topic", eventId, jsonObject);
            log.info("send payment eamil event: " + event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
