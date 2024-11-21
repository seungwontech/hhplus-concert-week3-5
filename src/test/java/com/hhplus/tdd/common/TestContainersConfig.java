package com.hhplus.tdd.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestContainersConfig {

    @Getter
    private static final KafkaContainer kafkaContainer;

    static {
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.1"));
    }

    @PostConstruct // 실행 시 컨테이너 시작
    public void startContainers() {
        kafkaContainer.start();
    }

    @PreDestroy // 종료 시 컨테이너 중단
    public void endContainers() {
        kafkaContainer.stop();
    }

    public static KafkaContainer getKafkaContainer() {
        return kafkaContainer;
    }

}
