package com.hhplus.tdd.balance;

import com.hhplus.tdd.balance.domain.model.Balance;
import com.hhplus.tdd.balance.domain.repository.BalanceRepository;
import com.hhplus.tdd.balance.domain.service.BalanceService;
import com.hhplus.tdd.balance.presentation.response.BalanceRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BalanceServiceIntegrationTest {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceService balanceService;

    @Test
    public void 포인트조회() {
        // given
        Long userId = 1L;
        // when
        Balance res = balanceService.getBalance(userId);

        // then
        assertThat(res).isNotNull();
        assertThat(res.getUserId()).isEqualTo(userId);
    }

    @Test
    public void 포인트사용성공() {
        // given
        Long userId = 1L;
        int amount = 500;
        // when
        BalanceRes res = balanceService.use(userId, amount);

        Balance balance = balanceRepository.getBalance(userId);

        // then
        assertThat(res).isNotNull();
        assertThat(res.getUserId()).isEqualTo(userId);
        assertThat(balance.getBalanceAmount()).isEqualTo(res.getBalanceAmount());
    }


    @Test
    public void 포인트충전성공() {
        // given
        Long userId = 1L;
        int amount = 500;
        // when
        Balance res = balanceService.charge(userId, amount);

        Balance balance = balanceRepository.getBalance(userId);

        // then
        assertThat(res).isNotNull();
        assertThat(res.getUserId()).isEqualTo(userId);
        assertThat(balance.getBalanceAmount()).isEqualTo(res.getBalanceAmount());
    }
}
