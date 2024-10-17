package com.hhplus.tdd.balance.infra.impl;

import com.hhplus.tdd.balance.domain.model.Balance;
import com.hhplus.tdd.balance.domain.repository.BalanceRepository;
import com.hhplus.tdd.balance.infra.BalanceJpaRepository;
import com.hhplus.tdd.balance.infra.entity.BalanceJpaEntity;
import com.hhplus.tdd.balance.infra.mapper.BalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BalanceRepositoryImpl implements BalanceRepository {

    private final BalanceJpaRepository balanceJpaRepository;

    private final BalanceMapper balanceMapper;

    @Override
    public Balance getBalance(Long userId) {
        BalanceJpaEntity entity = balanceJpaRepository.findByUserId(userId);
        if(entity == null) {
            return null;
        }
        return balanceMapper.toDomain(entity);
    }

    @Override
    public Balance save(Balance balance) {
        BalanceJpaEntity balanceJpaEntity = balanceMapper.toEntity(balance);
        BalanceJpaEntity savedEntity = balanceJpaRepository.save(balanceJpaEntity);
        return balanceMapper.toDomain(savedEntity);
    }

}
