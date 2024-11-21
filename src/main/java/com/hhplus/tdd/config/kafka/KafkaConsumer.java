package com.hhplus.tdd.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private String message = null;

    @KafkaListener(topics = "test-topic", groupId = "my-group")
    public void consume(String message) {
        this.message = message;
        log.info("Received message: {}", message);
    }

    public String getMessage() {
        return message;
    }
}