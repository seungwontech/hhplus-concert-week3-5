package com.hhplus.tdd.balance.infra;

import com.hhplus.tdd.balance.infra.entity.BalanceJpaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;


public interface BalanceJpaRepository extends JpaRepository<BalanceJpaEntity, Long> {
    // 비관적락
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    //BalanceJpaEntity findByUserId(Long userId);

    //낙관적락
    @Lock(LockModeType.OPTIMISTIC)
    BalanceJpaEntity findByUserId(Long userId);
}
