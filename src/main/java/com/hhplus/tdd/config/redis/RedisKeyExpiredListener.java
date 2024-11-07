package com.hhplus.tdd.config.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String ACTIVE_TOKENS_KEY = "active:tokens";

    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer, RedisTemplate<String, Object> redisTemplate) {
        super(listenerContainer);
        this.redisTemplate = redisTemplate;
    }
    @Override
    public void onMessage(Message message, byte[] pattern) {
        redisTemplate.opsForSet().remove(ACTIVE_TOKENS_KEY, message.toString());
    }
}
