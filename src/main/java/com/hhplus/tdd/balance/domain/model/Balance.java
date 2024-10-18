package com.hhplus.tdd.balance.domain.model;

import com.hhplus.tdd.balance.domain.exception.BalanceErrorResult;
import com.hhplus.tdd.balance.domain.exception.BalanceException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
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
            throw new BalanceException(BalanceErrorResult.BALANCE_LESS_THAN_ZERO);
        }

        if ((long) balanceAmount + amount > Integer.MAX_VALUE) {
            throw new BalanceException(BalanceErrorResult.BALANCE_LIMIT_AMOUNT);
        }

        return new Balance(balanceId, userId, balanceAmount + amount,  LocalDateTime.now());
    }

    public Balance use(int amount) {

        if (balanceAmount - amount < 0) {
            throw new BalanceException(BalanceErrorResult.BALANCE_EXCEEDS_AVAILABLE);
        }

        if (amount <= 0) {
            throw new BalanceException(BalanceErrorResult.BALANCE_LESS_THAN_ZERO);
        }
        return new Balance(balanceId, userId, balanceAmount - amount,  LocalDateTime.now());
    }
}
