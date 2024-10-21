package com.hhplus.tdd.concert.Integrate;

import com.hhplus.tdd.concert.application.usecase.ConcertPaymentUseCase;
import com.hhplus.tdd.concert.domain.repository.ConcertPaymentRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConcertPaymentUseCaseIntegrationTest {

    @Autowired
    private ConcertPaymentUseCase concertPaymentUseCase;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private ConcertPaymentRepository concertPaymentRepository;

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

}
