package com.hhplus.tdd.balance.infra;

import com.hhplus.tdd.balance.infra.entity.BalanceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BalanceJpaRepository extends JpaRepository<BalanceJpaEntity, Long> {
    BalanceJpaEntity findByUserId(Long userId);
}
