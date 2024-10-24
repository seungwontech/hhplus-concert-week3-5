package com.hhplus.tdd.concurrency;

import com.hhplus.tdd.concert.application.usecase.ConcertPaymentUseCase;
import com.hhplus.tdd.concert.application.usecase.ConcertReservationUseCase;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConcertConcurrencyTest {

    @Autowired
    private ConcertReservationUseCase concertReservationUseCase;

    @Autowired
    private ConcertPaymentUseCase concertPaymentUseCase;

    @Test
    public void 여러유저가하나의좌석을예약할때_한사람만성공() throws InterruptedException {
        int numberOfThreads = 5; // 동시에 10명의 유저가 시도
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);

        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long[] seatIds = {1L};

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    ConcertReservationReq req = new ConcertReservationReq(1L, seatIds);
                    concertReservationUseCase.execute(concertId, concertScheduleId, req);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    System.out.println("예약 실패: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
        assertEquals(1, successCount.get());
    }

    @Test
    public void 동일한유저로같은결제5번했을때1번만성공하는것을검증() throws InterruptedException {
        // given
        AtomicInteger failedOperations = new AtomicInteger(0);
        AtomicInteger successCount = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Long userId = 1L;
        Long[] concertReservationIds = new Long[]{165L};
        Long[] concertSeatIds = new Long[]{1L};

        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    ConcertPaymentReq req = new ConcertPaymentReq(userId, concertReservationIds, concertSeatIds);
                    concertPaymentUseCase.execute("ad815448-f58e-4003-b386-11f75bbb90b2", req);
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
        // then
        assertEquals(1, successCount.get(), "성공 카운트가 1");
        assertEquals(4, failedOperations.get(), "실패 카운트가 4");
    }
}
