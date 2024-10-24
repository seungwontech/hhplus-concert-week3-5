package com.hhplus.tdd.waitingqueue.domain.repository;


import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;


public interface WaitingQueueRepository {
    WaitingQueue getWaitingQueuePosition(Long userId);

    WaitingQueue save(WaitingQueue waitingQueue);

    Long getLastActivePositionOrThrow();

    WaitingQueue getWaitingQueueToken(String token);
}
