package com.hhplus.tdd.balance.domain.repository;

import com.hhplus.tdd.balance.domain.model.Balance;

public interface BalanceRepository {
    Balance getBalance(Long userId);

    Balance save(Balance charge);
}
