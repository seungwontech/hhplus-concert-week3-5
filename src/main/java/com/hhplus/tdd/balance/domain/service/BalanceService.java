package com.hhplus.tdd.balance.domain.service;

import com.hhplus.tdd.balance.domain.model.Balance;
import com.hhplus.tdd.balance.domain.repository.BalanceRepository;
import com.hhplus.tdd.balance.presentation.response.BalanceRes;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BalanceService {

    private final BalanceRepository balanceRepository;

    // 잔액 조회
    public BalanceRes getBalance(Long userId) {
        Balance result = balanceRepository.getBalance(userId);
        if (result == null) {
            log.warn("Balance not found for userId: {}", userId);
            throw new CoreException(ErrorType.BALANCE_NOT_FOUND, userId);
        }
        return BalanceRes.builder()
                .userId(result.getUserId())
                .balanceAmount(result.getBalanceAmount())
                .updatedAt(result.getBalanceUpdated())
                .build();
    }

    @Transactional
    public BalanceRes charge(Long userId, int amount) {
        Balance balance = balanceRepository.getBalance(userId);

        Balance updatedBalance = balance.charge(amount);

        balanceRepository.save(updatedBalance);

        return BalanceRes.builder()
                .userId(updatedBalance.getUserId())
                .balanceAmount(updatedBalance.getBalanceAmount())
                .updatedAt(updatedBalance.getBalanceUpdated())
                .build();
    }

    @Transactional
    public BalanceRes use(Long userId, int amount) {
        Balance balance = balanceRepository.getBalance(userId);

        Balance updatedBalance = balance.use(amount);

        balanceRepository.save(updatedBalance);

        return BalanceRes.builder()
                .userId(updatedBalance.getUserId())
                .balanceAmount(updatedBalance.getBalanceAmount())
                .updatedAt(updatedBalance.getBalanceUpdated())
                .build();
    }
}
