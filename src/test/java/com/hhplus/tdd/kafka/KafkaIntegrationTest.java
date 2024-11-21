package com.hhplus.tdd.kafka;

import com.hhplus.tdd.config.kafka.KafkaConsumer;
import com.hhplus.tdd.config.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KafkaIntegrationTest {

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumer consumer;


    @Test
    public void testKafka() throws Exception {
        String topic = "test-topic";
        String message = "Hello kafka";

        producer.sendMessage(topic, message);
        Thread.sleep(3000);

        // then
        assertThat(consumer.getMessage()).isEqualTo(message);
    }

}