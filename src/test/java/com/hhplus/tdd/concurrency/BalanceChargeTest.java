package com.hhplus.tdd.concurrency;

import com.hhplus.tdd.balance.domain.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BalanceChargeTest {

    @Autowired
    private BalanceService balanceService;

    @Test
    public void 동일한유저가5번충전했을때_합검증_비관적락() throws InterruptedException {
        // given
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failedOperations = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    balanceService.charge(1L, 1000);
                    successCount.incrementAndGet();
                } catch (RuntimeException e) {
                    failedOperations.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("처리 시간: " + duration + "ms");


        // then
        assertEquals(5, successCount.get(), "성공 카운트가 5");
        assertEquals(0, failedOperations.get(), "실패 카운트가 0");

    }
}
