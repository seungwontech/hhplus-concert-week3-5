package com.hhplus.tdd.concert.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "OUTBOX")
public class OutboxJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동 증가 기본 키

    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "topic", nullable = false, length = 255)
    private String topic; // Kafka 토픽 이름

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 메시지 생성 시간

    @Column(name = "status", nullable = false, length = 20)
    private String status; // 메시지 상태: WAITING, SENT

    @Column(name = "event_id", nullable = false, length = 255)
    private String eventId;

    @Builder
    public OutboxJpaEntity(Long id, String payload, String topic, String status, String eventId) {
        this.id = id;
        this.payload = payload;
        this.topic = topic;
        this.status = status;
        this.eventId = eventId;
        this.createdAt = LocalDateTime.now(); // 생성 시 현재 시간 자동 설정
    }
}