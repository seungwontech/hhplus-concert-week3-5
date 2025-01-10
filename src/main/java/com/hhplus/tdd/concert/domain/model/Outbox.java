package com.hhplus.tdd.concert.domain.model;


import com.hhplus.tdd.concert.domain.model.enums.OutboxStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Outbox {

    private Long id;
    private String payload;          // Kafka에 보낼 메시지 (JSON 형식)
    private String topic;            // Kafka 토픽 이름
    private LocalDateTime createdAt; // 메시지 생성 시간
    private OutboxStatus status;     // 메시지 상태: WAITING, PUBLISHED
    private String eventId;

    public static Outbox of(Long id, String payload, String topic, LocalDateTime createdAt, OutboxStatus status, String eventId) {
        return new Outbox(id, payload, topic, createdAt, status, eventId);
    }

}
