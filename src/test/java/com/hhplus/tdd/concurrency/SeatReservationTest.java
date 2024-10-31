package com.hhplus.tdd.concurrency;

import com.hhplus.tdd.concert.application.usecase.ConcertReservationUseCase;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SeatReservationTest {

    @Autowired
    private ConcertReservationUseCase concertReservationUseCase;

    @Test
    @DisplayName("여러 사용자가 동시에 좌석을 예약_낙관적락")
    public void seat_reservation_optimistic_test() throws Exception {
        int numberOfThreads = 1000; // 동시에 10명의 유저가 시도
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long[] seatIds = {1L};

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    ConcertReservationReq req = new ConcertReservationReq(1L, seatIds);
                    concertReservationUseCase.execute(concertId, concertScheduleId, req);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failureCount.incrementAndGet();
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

        assertEquals(1, successCount.get());
        assertEquals(numberOfThreads - 1, failureCount.get());

        // 낙관적락 처리 시간: 1411ms 1sec708

    }

    @Test
    @DisplayName("여러 사용자가 동시에 좌석을 예약_비관적락")
    public void seat_reservation_pessimistic_test() throws Exception {
        int numberOfThreads = 1000; // 동시에 10명의 유저가 시도
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long[] seatIds = {1L};

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    ConcertReservationReq req = new ConcertReservationReq(1L, seatIds);
                    concertReservationUseCase.execute_pessimistic(concertId, concertScheduleId, req);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failureCount.incrementAndGet();
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

        assertEquals(1, successCount.get());
        assertEquals(numberOfThreads - 1, failureCount.get());

        // 낙관적락 처리 시간: 1411ms 1sec708

    }
}
