package com.hhplus.tdd.config.redis;

import java.util.function.Supplier;

public interface LockManager {
    Object lock(String lockName, Supplier<Object> opertion) throws Throwable;
}
