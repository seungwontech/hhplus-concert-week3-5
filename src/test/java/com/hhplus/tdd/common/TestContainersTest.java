package com.hhplus.tdd.common;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public abstract class TestContainersTest {

    @DynamicPropertySource
    public static void testProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", () -> TestContainersConfig.getKafkaContainer().getBootstrapServers());
    }
}