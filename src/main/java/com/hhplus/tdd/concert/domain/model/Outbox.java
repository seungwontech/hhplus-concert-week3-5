package com.hhplus.tdd.concert.domain.model;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Outbox {

    private Long id; // 엔티티 기본 키
    private String payload; // Kafka에 보낼 메시지 (JSON 형식)
    private String topic; // Kafka 토픽 이름
    private LocalDateTime createdAt; // 메시지 생성 시간
    private String status; // 메시지 상태: WAITING, SENT
    private String eventId;

    @Builder
    public Outbox(Long id, String payload, String topic, LocalDateTime createdAt, String status, String eventId) {
        this.id = id;
        this.payload = payload;
        this.topic = topic;
        this.createdAt = createdAt;
        this.status = status;
        this.eventId = eventId;
    }

    public void sent() {
        this.status = "SENT";
    }


}
