package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.concert.presentation.response.PaymentRes;
import com.hhplus.tdd.waitingqueue.domain.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertPaymentUseCase {
    private final ConcertService concertService;
    private final WaitingQueueService waitingQueueService;

    public PaymentRes processConcertPayment(String token, Long concertId, ConcertPaymentReq paymentReq) {
        Long userId = paymentReq.getUserId();
        Long[] concertReservationIds = paymentReq.getConcertReservationId();
        Long[] concertSeatIds = paymentReq.getConcertSeatIds();

        List<ConcertSeat> concertSeats = concertService.getConcertSeatIdIn(concertSeatIds);

        String paymentStatus = "SUCCESS"; // 성공 상태
        LocalDateTime paymentDate = LocalDateTime.now(); // 현재 시간

        // 결제 리스트 생성
        List<ConcertPayment> concertPayments = new ArrayList<>();

        // 각 콘서트 예약 ID에 대해 결제 처리
        for (Long reservationId : concertReservationIds) {
            // ConcertPayment 엔티티 생성
            ConcertPayment concertPayment = ConcertPayment.of(
                    null,
                    userId,
                    reservationId,
                    concertSeats.get(0).getSeatPrice(),
                    paymentStatus,
                    paymentDate
            );

            // 결제 정보를 리스트에 추가
            concertPayments.add(concertPayment);
        }

        concertService.saveConcertPayments(concertPayments);
        waitingQueueService.expiredWaitingQueue(token);
        // 결제 응답 DTO 생성
        return PaymentRes.of(concertSeats.get(0).getSeatPrice() * concertSeats.size(), paymentStatus, paymentDate);
    }

}
