package com.hhplus.tdd.redistoken.scheduler;

import com.hhplus.tdd.redistoken.domain.repository.RedisTokenCoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisTokenScheduler {
    private final RedisTokenCoreRepository redisTokenCoreRepository;

    private final List<Long> userIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

    @Scheduled(fixedRate = 60000)
    public void scheduleTokenAddAndMove() {
        for (Long userId : userIds) {
            String token = "userToken" + userId;
            addTokenToWaiting(userId, token);
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        redisTokenCoreRepository.moveTokensToActive();
    }

    public Long addTokenToWaiting(Long userId, String userToken) {
        System.out.println("Adding token to waiting queue...");
        return redisTokenCoreRepository.addWaitingToken(userId, userToken);
    }
}
