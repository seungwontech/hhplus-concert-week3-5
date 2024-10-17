package com.hhplus.tdd.balance;

import com.hhplus.tdd.balance.domain.model.Balance;
import com.hhplus.tdd.balance.domain.repository.BalanceRepository;
import com.hhplus.tdd.balance.domain.service.BalanceService;
import com.hhplus.tdd.balance.domain.exception.BalanceErrorResult;
import com.hhplus.tdd.balance.domain.exception.BalanceException;
import com.hhplus.tdd.balance.presentation.response.BalanceRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Test
    public void 잔액조회_성공() {
        // given
        Long userId = 1L;
        Balance balance = new Balance(1L, 1L, 1000, LocalDateTime.now());
        doReturn(balance).when(balanceRepository).getBalance(userId);

        // when
        BalanceRes result = balanceService.getBalance(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(balance.getUserId());
        assertThat(result.getBalanceAmount()).isEqualTo(balance.getBalanceAmount());
    }

    @Test
    public void 잔액조회_실패_존재하지않음() {
        // given
        Long userId = 1L;
        doReturn(null).when(balanceRepository).getBalance(userId);

        // when
        BalanceException result = assertThrows(BalanceException.class, () -> {
            balanceService.getBalance(userId);
        });

        // then
        assertThat(result.getErrorResult()).isEqualTo(BalanceErrorResult.BALANCE_NOT_FOUND);

    }

    @Test
    public void 잔액충전_성공() {
        // given
        Long userId = 1L;
        int amount = 1000;
        Balance balance = new Balance(1L, userId, 1000, LocalDateTime.now());

        doReturn(balance).when(balanceRepository).getBalance(userId);

        doReturn(new Balance(balance.getBalanceId(), userId, 2000, LocalDateTime.now()))
                .when(balanceRepository).save(any(Balance.class));

        //when
        BalanceRes result = balanceService.charge(userId, amount);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(balance.getUserId());
        assertThat(result.getBalanceAmount()).isEqualTo(2000);
    }

    @Test
    public void 잔액충전_실패_충전금액이0보다작을때() {
        // given
        Long userId = 1L;
        int amount = -500; // 0보다 작은 금액
        Balance balance = new Balance(1L, userId, 1000, LocalDateTime.now());

        doReturn(balance).when(balanceRepository).getBalance(userId);

        // when & then
        BalanceException exception = assertThrows(BalanceException.class, () -> balanceService.charge(userId, amount));
        assertEquals(BalanceErrorResult.BALANCE_LESS_THAN_ZERO, exception.getErrorResult());
    }

    @Test
    public void 잔액충전_실패_충전금액최대일때() {
        // given
        Long userId = 1L;
        int amount = Integer.MAX_VALUE; // 0보다 작은 금액
        Balance balance = new Balance(1L, userId, 1000, LocalDateTime.now());

        doReturn(balance).when(balanceRepository).getBalance(userId);

        // when & then
        BalanceException exception = assertThrows(BalanceException.class, () -> balanceService.charge(userId, amount));
        assertEquals(BalanceErrorResult.BALANCE_LIMIT_AMOUNT, exception.getErrorResult());
    }

    @Test
    public void 잔액사용_성공() {
        // given
        Long userId = 1L;
        int amount = 1000;
        Balance balance = new Balance(1L, userId, 1000, LocalDateTime.now());

        doReturn(balance).when(balanceRepository).getBalance(userId);

        doReturn(new Balance(balance.getBalanceId(), userId, 0, LocalDateTime.now()))
                .when(balanceRepository).save(any(Balance.class));

        //when
        BalanceRes result = balanceService.use(userId, amount);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(balance.getUserId());
        assertThat(result.getBalanceAmount()).isEqualTo(0);
    }

    @Test
    public void 잔액보다사용금액클때_실패() {
        // given
        Long userId = 1L;
        int amount = Integer.MIN_VALUE;
        Balance balance = new Balance(1L, userId, 1000, LocalDateTime.now());

        doReturn(balance).when(balanceRepository).getBalance(userId);

        // when & then
        BalanceException exception = assertThrows(BalanceException.class, () -> balanceService.use(userId, amount));
        assertEquals(BalanceErrorResult.BALANCE_EXCEEDS_AVAILABLE, exception.getErrorResult());
    }
}
