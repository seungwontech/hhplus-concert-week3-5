package com.hhplus.tdd.concert.unit;

import com.hhplus.tdd.concert.application.usecase.ConcertPaymentUseCase;
import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.model.PaymentStatus;
import com.hhplus.tdd.concert.domain.repository.ConcertPaymentRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.concert.presentation.response.PaymentRes;
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
        ConcertSeat concertSeat1 = new ConcertSeat(1L, 1L, 1L, 10, 100, "N");
        ConcertSeat concertSeat2 = new ConcertSeat(2L, 1L, 1L, 10, 100, "N");

        List<ConcertSeat> concertSeats = Arrays.asList(concertSeat1, concertSeat2);

        // Mock 동작 설정
        when(concertSeatRepository.getConcertSeatIdIn(concertSeatIds)).thenReturn(concertSeats);
        WaitingQueue mockQueue = new WaitingQueue(1L, userId, token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), "ACTIVE");
        when(waitingQueueRepository.getWaitingQueueToken(token)).thenReturn(mockQueue);

        // 메서드 실행
        PaymentRes paymentRes = concertPaymentUseCase.execute(token, paymentReq);

        // 결제 정보 저장 확인
        verify(concertPaymentRepository, times(1)).saveAll(any());

        // 총 금액 확인 (100 * 2 = 200)
        assertEquals(200, paymentRes.getPaymentAmount());

        // 결제 상태 확인
        assertEquals("SUCCESS", paymentRes.getPaymentStatus());
    }

    @Test
    void 좌석별_가격계산_예약정보_결제정보_연결_성공() {
        // given
        Long userId = 1L;
        Long[] concertSeatIds = new Long[]{1L, 2L};
        Long[] concertReservationIds = new Long[]{10L, 20L};
        ConcertPaymentReq paymentReq = ConcertPaymentReq.of(userId, concertReservationIds, concertSeatIds);

        ConcertSeat seat1 = new ConcertSeat(1L, 1L, 1L, 1, 40000, "N");
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, 1L, 2, 40000, "N");

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
        Long[] concertSeatIds = new Long[]{1L, 2L};
        ConcertSeat seat1 = new ConcertSeat(1L, 1L, 1L, 1, 40000, "N");
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, 1L, 2, 40000, "N");

        doReturn(Arrays.asList(seat1, seat2)).when(concertSeatRepository).getConcertSeatIdIn(concertSeatIds);

        // when
        List<ConcertSeat> seats = concertPaymentUseCase.getConcertSeatIdIn(concertSeatIds);

        // then
        assertEquals(2, seats.size());
        assertEquals(40000, seats.get(0).getSeatPrice());
        assertEquals(40000, seats.get(1).getSeatPrice());
    }

    @Test
    void 좌석정보없을때_예외발생() {
        // given
        Long[] concertSeatIds = new Long[]{1L, 2L};

        doReturn(null).when(concertSeatRepository).getConcertSeatIdIn(concertSeatIds);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            concertPaymentUseCase.getConcertSeatIdIn(concertSeatIds);
        });

        assertEquals("좌석 정보가 없습니다.", exception.getMessage());
    }


    @Test
    void 좌석가격정보없을때_예외발생() {
        // given
        Long userId = 1L;
        Long[] concertSeatIds = {1L, 2L};
        Long[] concertReservationIds = {1L, 2L};

        ConcertPaymentReq paymentReq = ConcertPaymentReq.of(userId, concertReservationIds, concertSeatIds);

        // 좌석 가격이 없음을 나타내기 위해 빈 리스트를 반환하도록 설정
        doReturn(Collections.emptyList()).when(concertSeatRepository).getConcertSeatIdIn(concertSeatIds);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            concertPaymentUseCase.execute("Token", paymentReq);
        });

        assertEquals("해당 좌석에 대한 가격 정보가 없습니다: 좌석 ID = " + concertSeatIds[0], exception.getMessage());
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