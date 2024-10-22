package com.hhplus.tdd.concert.integrate;

import com.hhplus.tdd.concert.application.usecase.ConcertPaymentUseCase;
import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.domain.model.PaymentStatus;
import com.hhplus.tdd.concert.domain.repository.ConcertPaymentRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.concert.presentation.response.PaymentRes;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueueStatus;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcertPaymentUseCaseIntegrationTest {

    @Autowired
    private ConcertPaymentUseCase concertPaymentUseCase;

    @Autowired
    private ConcertPaymentRepository concertPaymentRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

    private Long userId;
    private Long[] concertReservationIds;
    private Long[] concertSeatIds;

    @BeforeEach
    void setUp() {

        userId = 1L; // 예시 사용자 ID
        concertReservationIds = new Long[]{1L, 2L}; // 예시 예약 ID 배열
        concertSeatIds = new Long[]{1L, 2L}; // 예시 좌석 ID 배열

    }
    @Test
    void testExecuteSuccess() {
        // given
        ConcertPaymentReq request = ConcertPaymentReq.of(userId, concertReservationIds, concertSeatIds);

        // when
        PaymentRes response = concertPaymentUseCase.execute("token12345", request);

        // then
        assertThat(response.getPaymentAmount()).isEqualTo(2000); // 총 결제 금액
        assertThat(response.getPaymentStatus()).isEqualTo(PaymentStatus.SUCCESS.toString());
        assertThat(response.getPaymentDate()).isNotNull();

        // 결제 정보가 데이터베이스에 저장되었는지 확인
        List<ConcertPayment> payments = concertPaymentRepository.findAll();
        assertThat(payments).hasSize(concertReservationIds.length);

        for (ConcertPayment payment : payments) {
            assertThat(payment.getUserId()).isEqualTo(userId);
            assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.SUCCESS.toString());
            assertThat(payment.getPaymentAmount()).isEqualTo(1000); // 각 좌석 가격
            assertThat(payment.getPaymentDate()).isNotNull();
        }

        // 대기열 상태가 EXPIRED로 변경되었는지 확인
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueToken("token12345");
        assertThat(waitingQueue).isNotNull();
        assertThat(waitingQueue.getTokenStatus()).isEqualTo(WaitingQueueStatus.EXPIRED.toString());
    }
}
