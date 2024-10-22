package com.hhplus.tdd.balance.infra.entity;

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
@Table(name = "BALANCE")
public class BalanceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private Long balanceId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "balance_amount", nullable = false)
    private int balanceAmount;

    @Column(name = "balance_updated", nullable = false)
    private LocalDateTime balanceUpdated;

    public static BalanceJpaEntity of(Long balanceId, Long userId, int balanceAmount, LocalDateTime balanceUpdated) {
        return new BalanceJpaEntity(balanceId, userId, balanceAmount, balanceUpdated);
    }
}
