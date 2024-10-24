package com.hhplus.tdd.concert.unit;

import com.hhplus.tdd.concert.application.usecase.ConcertPaymentUseCase;
import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.domain.model.ConcertPaymentResult;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.model.PaymentStatus;
import com.hhplus.tdd.concert.domain.repository.ConcertPaymentRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueueStatus;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConcertPaymentUseCaseTest {

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @Mock
    private ConcertPaymentRepository concertPaymentRepository;

    @Mock
    private WaitingQueueRepository waitingQueueRepository;

    @InjectMocks
    private ConcertPaymentUseCase concertPaymentUseCase;

    @Test
    void 결제처리_성공() {
        String token = "a1as2s3d";
        Long userId = 1L;
        Long[] concertReservationIds = {1L, 2L};
        Long[] concertSeatIds = {1L, 2L};

        ConcertPaymentReq paymentReq = ConcertPaymentReq.of(userId, concertReservationIds, concertSeatIds);

        // Mock 데이터 설정
        ConcertSeat concertSeat1 = new ConcertSeat(1L, 1L, 1L, 10, 100, "N", 1L);
        ConcertSeat concertSeat2 = new ConcertSeat(2L, 1L, 1L, 10, 100, "N", 1L);

        List<ConcertSeat> concertSeats = Arrays.asList(concertSeat1, concertSeat2);

        // Mock 동작 설정
        when(concertSeatRepository.getConcertSeatIdIn(concertSeatIds)).thenReturn(concertSeats);
        WaitingQueue mockQueue = new WaitingQueue(1L, userId, token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), "ACTIVE");
        when(waitingQueueRepository.getWaitingQueueToken(token)).thenReturn(mockQueue);

        // 메서드 실행
        ConcertPaymentResult concertPaymentResult = concertPaymentUseCase.execute(token, paymentReq);

        // 결제 정보 저장 확인
        verify(concertPaymentRepository, times(1)).saveAll(any());

        // 총 금액 확인 (100 * 2 = 200)
        assertEquals(200, concertPaymentResult.getTotalPrice());

        // 결제 상태 확인
        assertEquals("SUCCESS", concertPaymentResult.getPaymentStatus());
    }

    @Test
    void 좌석별_가격계산_예약정보_결제정보_연결_성공() {
        // given
        Long userId = 1L;
        Long[] concertSeatIds = new Long[]{1L, 2L};
        Long[] concertReservationIds = new Long[]{10L, 20L};
        ConcertPaymentReq paymentReq = ConcertPaymentReq.of(userId, concertReservationIds, concertSeatIds);

        ConcertSeat seat1 = new ConcertSeat(1L, 1L, 1L, 1, 40000, "N", 1L);
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, 1L, 2, 40000, "N",1L);

        // when
        List<ConcertPayment> payments = concertPaymentUseCase.processConcertPayment(paymentReq, Arrays.asList(seat1, seat2));

        // then
        assertEquals(2, payments.size());
        assertEquals(40000, payments.get(0).getPaymentAmount());
        assertEquals(40000, payments.get(1).getPaymentAmount());
    }

    @Test
    void 결제정보저장_성공() {
        // given
        List<ConcertPayment> payments = Arrays.asList(
                new ConcertPayment(1L, 1L, 10L, 40000, PaymentStatus.SUCCESS.toString(), LocalDateTime.now()),
                new ConcertPayment(2L, 1L, 20L, 40000, PaymentStatus.SUCCESS.toString(), LocalDateTime.now())
        );

        // when
        concertPaymentUseCase.saveConcertPayments(payments);

        // then
        verify(concertPaymentRepository).saveAll(payments);
    }

    @Test
    void 좌석정보조회_성공() {
        // given
        Long userId = 1L;
        Long[] concertSeatIds = new Long[]{1L, 2L};
        Long[] concertReservationIds = {1L, 2L};

        ConcertSeat seat1 = new ConcertSeat(1L, 1L, 1L, 1, 40000, "N",1L);
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, 1L, 2, 40000, "N",1L);

        doReturn(Arrays.asList(seat1, seat2)).when(concertSeatRepository).getConcertSeatIdIn(concertSeatIds);
        String token = "someToken";
        WaitingQueue waitingQueue = WaitingQueue.of(1L, 1L, token, LocalDateTime.now().plusMinutes(1), LocalDateTime.now(), WaitingQueueStatus.EXPIRED.toString());

        doReturn(waitingQueue).when(waitingQueueRepository).getWaitingQueueToken(token);

        ConcertPaymentReq paymentReq = ConcertPaymentReq.of(userId, concertReservationIds, concertSeatIds);

        // when
        ConcertPaymentResult result = concertPaymentUseCase.execute("someToken", paymentReq);

        // then
        assertEquals(80000, result.getTotalPrice());
    }

    @Test
    void 좌석정보없을때_예외발생() {
        // given
        Long userId = 1L;
        Long[] concertSeatIds = new Long[]{1L, 2L};
        Long[] concertReservationIds = {1L, 2L};

        doReturn(Collections.emptyList()).when(concertSeatRepository).getConcertSeatIdIn(concertSeatIds);

        ConcertPaymentReq paymentReq = ConcertPaymentReq.of(userId, concertReservationIds, concertSeatIds);

        // when & then
        CoreException exception = assertThrows(CoreException.class, () -> {
            ConcertPaymentResult result = concertPaymentUseCase.execute("token", paymentReq);
        });

        assertEquals(ErrorType.CONCERT_SEAT_NOT_FOUND, exception.getErrorType());
    }


    @Test
    void 대기열만료처리_성공() {
        // given
        String token = "someToken";
        WaitingQueue waitingQueue = WaitingQueue.of(1L, 1L, token, LocalDateTime.now().plusMinutes(1), LocalDateTime.now(), WaitingQueueStatus.EXPIRED.toString());

        doReturn(waitingQueue).when(waitingQueueRepository).getWaitingQueueToken(token);

        // when
        concertPaymentUseCase.expiredWaitingQueue(token);

        // then
        verify(waitingQueueRepository).save(waitingQueue);
        assertEquals(WaitingQueueStatus.EXPIRED.toString(), waitingQueue.getTokenStatus());
    }

}
