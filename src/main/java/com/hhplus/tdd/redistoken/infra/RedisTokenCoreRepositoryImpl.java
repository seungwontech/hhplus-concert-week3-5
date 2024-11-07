package com.hhplus.tdd.redistoken.infra;

import com.hhplus.tdd.redistoken.domain.repository.RedisTokenCoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class RedisTokenCoreRepositoryImpl implements RedisTokenCoreRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private final String WAITING_TOKENS_KEY = "waiting:tokens";
    private final String ACTIVE_TOKENS_KEY = "active:tokens";


    @Override
    public Long addWaitingToken(Long userId, String userToken) {
        String userInfo = userToken;
        long score = System.currentTimeMillis();

        redisTemplate.opsForZSet().add(WAITING_TOKENS_KEY, userInfo, score);
        redisTemplate.opsForHash().put(userToken, "userId", String.valueOf(userId));
        return redisTemplate.opsForZSet().rank(WAITING_TOKENS_KEY, userInfo);
    }

    @Override
    public void moveTokensToActive() {
        Set<String> waitingTokens = redisTemplate.opsForZSet().range(WAITING_TOKENS_KEY, 0, 4);

        if (waitingTokens != null) {
            for (String token : waitingTokens) {
                redisTemplate.opsForSet().add(ACTIVE_TOKENS_KEY, token);
                redisTemplate.opsForZSet().remove(WAITING_TOKENS_KEY, token);
                redisTemplate.expire(token, Duration.ofSeconds(5));
            }
        }
    }

}