package com.hhplus.tdd.balance.infra.mapper;

import com.hhplus.tdd.balance.domain.model.Balance;
import com.hhplus.tdd.balance.infra.entity.BalanceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class BalanceMapper {
    public Balance toDomain(BalanceJpaEntity entity) {
        return Balance.of(entity.getBalanceId()
                , entity.getUserId()
                , entity.getBalanceAmount()
                , entity.getBalanceUpdated()
                , entity.getVersion()
        );
    }

    public BalanceJpaEntity toEntity(Balance balance) {
        return BalanceJpaEntity.of(balance.getBalanceId()
                , balance.getUserId()
                , balance.getBalanceAmount()
                , balance.getBalanceUpdated()
                , balance.getVersion()
        );
    }
}
