package com.hhplus.tdd.balance.domain.model;

import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Slf4j
public class Balance {
    private Long balanceId;
    private Long userId;
    private int balanceAmount;
    private LocalDateTime balanceUpdated;

    public static Balance of(Long balanceId, Long userId, int balanceAmount, LocalDateTime balanceUpdated) {
        return new Balance(balanceId, userId, balanceAmount, balanceUpdated);
    }
    public Balance charge(int amount) {

        if (amount <= 0) {
            log.warn("충전 금액은 0보다 커야 합니다. userId: {}, amount: {} ", userId, amount);
            throw new CoreException(ErrorType.BALANCE_LESS_THAN_ZERO, amount);
        }

        if ((long) balanceAmount + amount > Integer.MAX_VALUE) {
            log.warn("충전 금액이 초과되었습니다. userId: {}, amount: {} ", userId, amount);
            throw new CoreException(ErrorType.BALANCE_LIMIT_AMOUNT, amount);
        }

        return new Balance(balanceId, userId, balanceAmount + amount,  LocalDateTime.now());
    }

    public Balance use(int amount) {

        if (amount <= 0) {
            log.warn("사용 금액은 0보다 커야합니다.userId: {}, amount: {}", userId, amount);
            throw new CoreException(ErrorType.BALANCE_LESS_THAN_ZERO, amount);
        }

        if (balanceAmount - amount < 0) {
            log.warn("사용하려는 금액이 잔액을 초과할 수 없습니다. userId: {}, amount: {}", userId, amount);
            throw new CoreException(ErrorType.BALANCE_EXCEEDS_AVAILABLE, amount);
        }

        return new Balance(balanceId, userId, balanceAmount - amount,  LocalDateTime.now());
    }
}
