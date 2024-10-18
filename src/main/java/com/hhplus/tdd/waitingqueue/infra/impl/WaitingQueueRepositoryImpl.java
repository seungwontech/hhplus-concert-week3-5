package com.hhplus.tdd.waitingqueue.infra.impl;

import com.hhplus.tdd.waitingqueue.domain.exception.WaitingQueueErrorResult;
import com.hhplus.tdd.waitingqueue.domain.exception.WaitingQueueException;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import com.hhplus.tdd.waitingqueue.infra.WaitingQueueJpaRepository;
import com.hhplus.tdd.waitingqueue.infra.entity.WaitingQueueJpaEntity;
import com.hhplus.tdd.waitingqueue.infra.mapper.WaitingQueueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class WaitingQueueRepositoryImpl implements WaitingQueueRepository {

    private final WaitingQueueJpaRepository waitingQueueJpaRepository;

    private final WaitingQueueMapper waitingQueueMapper;

    @Override
    public WaitingQueue getWaitingQueuePosition(Long userId) {
        WaitingQueueJpaEntity entity = waitingQueueJpaRepository.findByUserId(userId);
        if (entity == null) {
            return null;
        }
        return waitingQueueMapper.toDomain(entity);
    }

    @Override
    public WaitingQueue save(WaitingQueue waitingQueue) {
        WaitingQueueJpaEntity entity = waitingQueueMapper.toEntity(waitingQueue);

        WaitingQueueJpaEntity savedEntity = waitingQueueJpaRepository.save(entity);

        return waitingQueueMapper.toDomain(savedEntity);
    }

    @Override
    public WaitingQueue getWaitingQueueToken(String token) {
        WaitingQueueJpaEntity entity = waitingQueueJpaRepository.findByToken(token);
        if (entity == null) {
            return null;
        }
        return waitingQueueMapper.toDomain(entity);
    }

    @Override
    public Long getLastActivePosition() {
        return waitingQueueJpaRepository.getLastActivePosition()
               .orElseThrow(() -> new WaitingQueueException(WaitingQueueErrorResult.WAITINGQUEUE_NOT_FOUND)); // 예외 처리 추가

    }


}
