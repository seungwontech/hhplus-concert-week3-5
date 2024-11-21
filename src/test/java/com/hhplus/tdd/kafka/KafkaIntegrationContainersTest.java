package com.hhplus.tdd.kafka;

import com.hhplus.tdd.common.TestContainersTest;
import com.hhplus.tdd.config.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
public class KafkaIntegrationContainersTest extends TestContainersTest {

    @Autowired
    private KafkaProducer producer;

    private final AtomicInteger receivedMessageCount = new AtomicInteger(0);
    final String TOPIC = "test-topic";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void 카프카_메시지가_정상적으로_발행되고_수신되는지_확인() {

        int messageCount = 50;
        for(int i = 0; i < messageCount; i++) {
            producer.sendMessage(TOPIC , "Hello, kafka!" + i);

        }

        await().atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(receivedMessageCount.get()).isEqualTo(messageCount));
    }

    @KafkaListener(topics = TOPIC, groupId = "test-group")
    public void consumeTestMessage(String message) {
        logger.info("Received message: {}", message);
        receivedMessageCount.incrementAndGet();
    }


}