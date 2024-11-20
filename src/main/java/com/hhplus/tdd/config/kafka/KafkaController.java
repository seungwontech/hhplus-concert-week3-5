package com.hhplus.tdd.config.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kafka")
public class KafkaController {
    private final KafkaProducer kafkaProducer;

    @PostMapping("/send")
    public void sendMessage(@RequestParam String message) {
        kafkaProducer.sendMessage("my-topic", message);
    }
}