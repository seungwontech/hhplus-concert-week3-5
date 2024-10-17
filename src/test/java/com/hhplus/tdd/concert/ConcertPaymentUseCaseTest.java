package com.hhplus.tdd.concert;

import com.hhplus.tdd.concert.application.usecase.ConcertPaymentUseCase;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.concert.presentation.response.PaymentRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConcertPaymentUseCaseTest {

    @Mock
    private ConcertService concertService;

    @InjectMocks
    private ConcertPaymentUseCase concertPaymentUseCase;

    @Test
    void testProcessConcertPayment_Success() {
        Long concertId = 1L;
        Long userId = 1L;
        Long[] concertReservationIds = {1L, 2L};
        Long[] concertSeatIds = {10L, 20L};

        ConcertPaymentReq paymentReq = ConcertPaymentReq.of(userId, concertReservationIds, concertSeatIds);

        ConcertSeat concertSeat1 = new ConcertSeat(1L, 1L, 1L, 10, 100, "N");
        ConcertSeat concertSeat2 = new ConcertSeat(1L, 1L, 1L, 10, 100, "N");

        List<ConcertSeat> concertSeats = Arrays.asList(concertSeat1, concertSeat2);

        when(concertService.getConcertSeatIdIn(concertSeatIds)).thenReturn(concertSeats);

        PaymentRes paymentRes = concertPaymentUseCase.processConcertPayment(concertId, paymentReq);

        verify(concertService, times(1)).saveConcertPayments(any()); // 결제 정보 저장 확인
        assertEquals(200, paymentRes.getPaymentAmount()); // 총 금액 확인 (10000 * 2)
        assertEquals("SUCCESS", paymentRes.getPaymentStatus()); // 결제 상태 확인
    }
}
