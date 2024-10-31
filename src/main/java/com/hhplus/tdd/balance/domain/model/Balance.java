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
    private Long version;

    public static Balance of(Long balanceId, Long userId, int balanceAmount, LocalDateTime balanceUpdated, Long version) {
        return new Balance(balanceId, userId, balanceAmount, balanceUpdated, version);
    }
    public Balance charge(int amount) {

        if (amount <= 0) {
            throw new CoreException(ErrorType.BALANCE_LESS_THAN_ZERO, amount);
        }

        if ((long) balanceAmount + amount > Integer.MAX_VALUE) {
            throw new CoreException(ErrorType.BALANCE_LIMIT_AMOUNT, amount);
        }

        return new Balance(balanceId, userId, balanceAmount + amount,  LocalDateTime.now(), version);
    }

    public Balance use(int amount) {

        if (amount <= 0) {
            throw new CoreException(ErrorType.BALANCE_LESS_THAN_ZERO, amount);
        }

        if (balanceAmount - amount < 0) {
            throw new CoreException(ErrorType.BALANCE_EXCEEDS_AVAILABLE, amount);
        }

        return new Balance(balanceId, userId, balanceAmount - amount,  LocalDateTime.now(), version);
    }
}
