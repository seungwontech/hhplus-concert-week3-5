package com.hhplus.tdd.concert.infra.entity;

import com.hhplus.tdd.concert.domain.model.enums.OutboxStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "OUTBOX")
public class OutboxJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "topic", nullable = false, length = 255)
    private String topic;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false, length = 20)
    private OutboxStatus status;

    @Column(name = "event_id", nullable = false, length = 255)
    private String eventId;

    public static OutboxJpaEntity of(Long id, String payload, String topic, LocalDateTime createdAt, OutboxStatus status, String eventId) {
        return new OutboxJpaEntity(id, payload, topic, createdAt, status, eventId);
    }

}