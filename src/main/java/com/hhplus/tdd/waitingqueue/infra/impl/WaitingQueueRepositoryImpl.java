package com.hhplus.tdd.waitingqueue.infra.impl;

import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import com.hhplus.tdd.waitingqueue.infra.WaitingQueueJpaRepository;
import com.hhplus.tdd.waitingqueue.infra.entity.WaitingQueueJpaEntity;
import com.hhplus.tdd.waitingqueue.infra.mapper.WaitingQueueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class WaitingQueueRepositoryImpl implements WaitingQueueRepository {

    private final WaitingQueueJpaRepository waitingQueueJpaRepository;

    private final WaitingQueueMapper waitingQueueMapper;

    @Override
    public WaitingQueue getWaitingQueuePosition(Long userId) {
        WaitingQueueJpaEntity entity = waitingQueueJpaRepository.findByUserId(userId);
        if (entity == null) {
            log.warn("대기열을 찾을 수 없습니다. userId: {}", userId);
            throw new CoreException(ErrorType.WAITING_QUEUE_NOT_FOUND, userId);
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
        return waitingQueueMapper.toDomain(entity);
    }

    @Override
    public Long getLastActivePositionOrThrow() {
        return waitingQueueJpaRepository.getLastActivePosition()
                .orElseThrow(() -> new CoreException(ErrorType.WAITING_QUEUE_NOT_FOUND, null));

    }

}
