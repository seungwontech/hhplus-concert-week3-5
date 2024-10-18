package com.hhplus.tdd.balance;

import com.hhplus.tdd.balance.domain.exception.BalanceErrorResult;
import com.hhplus.tdd.balance.domain.exception.BalanceException;
import com.hhplus.tdd.balance.domain.model.Balance;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceTest {

    @Test
    void 충전금액_잔액증가_성공() {
        // Given
        Balance balance = new Balance(1L, 1L, 1000, LocalDateTime.now());
        int chargeAmount = 500;

        // When
        Balance newBalance = balance.charge(chargeAmount);

        // Then
        assertEquals(1500, newBalance.getBalanceAmount());
        assertNotNull(newBalance.getBalanceUpdated());
        assertEquals(balance.getBalanceId(), newBalance.getBalanceId());
        assertEquals(balance.getUserId(), newBalance.getUserId());
    }

    @Test
    void 충전금액_금액이0이거나0보다작을때_실패() {
        // Given
        Balance balance = new Balance(1L, 1L, 1000, LocalDateTime.now());
        int chargeAmount1 = 0;

        // When & Then
        BalanceException exception = assertThrows(BalanceException.class, () -> balance.charge(chargeAmount1));
        assertEquals(BalanceErrorResult.BALANCE_LESS_THAN_ZERO, exception.getErrorResult());

        // Given
        int chargeAmount2 = -100;

        // When & Then
        exception = assertThrows(BalanceException.class, () -> balance.charge(chargeAmount2));
        assertEquals(BalanceErrorResult.BALANCE_LESS_THAN_ZERO, exception.getErrorResult());
    }

    @Test
    void 잔액최대로인해충전_실패() {
        // Given
        Balance balance = new Balance(1L, 1L, Integer.MAX_VALUE, LocalDateTime.now());
        int chargeAmount = 1;

        // When & Then
        BalanceException exception = assertThrows(BalanceException.class, () -> balance.charge(chargeAmount));
        assertEquals(BalanceErrorResult.BALANCE_LIMIT_AMOUNT, exception.getErrorResult());
    }

    @Test
    void 잔액과충전금액이최대로인해_실패() {
        // Given
        Balance balance = new Balance(1L, 1L, Integer.MAX_VALUE - 1, LocalDateTime.now());
        int chargeAmount = 3;

        // When & Then
        BalanceException exception = assertThrows(BalanceException.class, () -> balance.charge(chargeAmount));
        assertEquals(BalanceErrorResult.BALANCE_LIMIT_AMOUNT, exception.getErrorResult());
    }

    @Test
    void 잔액사용이_성공해야한다() {
        // Given
        Balance balance = new Balance(1L, 1L, 500, LocalDateTime.now());
        int useAmount = 300;

        // When
        Balance updatedBalance = balance.use(useAmount);

        // Then
        assertEquals(200, updatedBalance.getBalanceAmount());
        assertNotNull(updatedBalance.getBalanceUpdated());
    }

    @Test
    void 잔액보다사용금액이크면_실패() {
        // Given
        Balance balance = new Balance(1L, 1L, 500, LocalDateTime.now());
        int useAmount = 600;

        // When & Then
        BalanceException exception = assertThrows(BalanceException.class, () -> balance.use(useAmount));
        assertEquals(BalanceErrorResult.BALANCE_EXCEEDS_AVAILABLE, exception.getErrorResult());
    }
}