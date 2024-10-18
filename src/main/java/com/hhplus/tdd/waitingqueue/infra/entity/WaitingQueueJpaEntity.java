package com.hhplus.tdd.waitingqueue.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "WAITING_QUEUE")
public class WaitingQueueJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queue_id")
    private Long queueId; // 대기열 ID

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 ID

    @Column(name = "token", nullable = false, length = 255)
    private String token; // 토큰 값

    @Column(name = "token_expiry", nullable = false)
    private LocalDateTime tokenExpiry; // 토큰 만료 시간

    @Column(name = "token_created", nullable = false)
    private LocalDateTime tokenCreated; // 토큰 생성 시간

    @Column(name = "token_status", nullable = false, length = 50)
    private String tokenStatus; // 토큰 상태

    public static WaitingQueueJpaEntity of(Long queueId, Long userId, String token, LocalDateTime tokenExpiry, LocalDateTime tokenCreated, String tokenStatus) {
        return new WaitingQueueJpaEntity(queueId, userId, token, tokenExpiry, tokenCreated, tokenStatus);
    }
}