package com.hhplus.tdd.redistoken.domain.repository;

public interface RedisTokenCoreRepository {

    Long addWaitingToken(Long userId, String userToken);

    void moveTokensToActive();

}