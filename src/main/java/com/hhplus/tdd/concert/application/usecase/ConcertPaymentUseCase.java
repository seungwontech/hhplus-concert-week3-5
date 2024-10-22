package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.model.PaymentStatus;
import com.hhplus.tdd.concert.domain.repository.ConcertPaymentRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.concert.presentation.response.PaymentRes;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ConcertPaymentUseCase {

    private final WaitingQueueRepository waitingQueueRepository;

    private final ConcertPaymentRepository concertPaymentRepository;

    private final ConcertSeatRepository concertSeatRepository;

    public PaymentRes execute(String token, ConcertPaymentReq concertPaymentReq) {

        List<ConcertSeat> concertSeats = getConcertSeatIdIn(concertPaymentReq.getConcertSeatIds());

        List<ConcertPayment> concertPayments = processConcertPayment(concertPaymentReq, concertSeats);

        saveConcertPayments(concertPayments);

        expiredWaitingQueue(token);

        Integer totalPrice = 0;
        for (ConcertPayment payment : concertPayments) {
            totalPrice += payment.getPaymentAmount();
        }

        return PaymentRes.of(totalPrice, PaymentStatus.SUCCESS.toString(), LocalDateTime.now());
    }

    // 좌석별 가격을 계산하고, 예약 정보와 결제 정보를 매칭하여 ConcertPayment 객체 생성.
    public List<ConcertPayment> processConcertPayment(ConcertPaymentReq concertPaymentReq, List<ConcertSeat> concertSeats) {
        Long userId = concertPaymentReq.getUserId();
        Long[] concertSeatIds = concertPaymentReq.getConcertSeatIds();
        Long[] concertReservationIds = concertPaymentReq.getConcertReservationId();

        Map<Long, Integer> reservedSeatPrices = new HashMap<>();
        Set<Long> concertSeatIdSet = new HashSet<>(Arrays.asList(concertSeatIds));
        for (ConcertSeat seat : concertSeats) {
            if (concertSeatIdSet.contains(seat.getConcertSeatId())) {
                reservedSeatPrices.put(seat.getConcertSeatId(), seat.getSeatPrice());
            }
        }

        List<ConcertPayment> concertPayments = new ArrayList<>();
        String paymentStatus = String.valueOf(PaymentStatus.SUCCESS);
        LocalDateTime paymentDate = LocalDateTime.now();

        for (int i = 0; i < concertReservationIds.length; i++) {
            Long seatId = concertSeatIds[i];   // 해당 좌석 ID
            Long reservationId = concertReservationIds[i];  // 해당 예약 ID
            Integer seatPrice = reservedSeatPrices.get(seatId); // 좌석 가격 가져오기

            if (seatPrice == null) {
                throw new IllegalArgumentException("해당 좌석에 대한 가격 정보가 없습니다: 좌석 ID = " + seatId);
            }

            ConcertPayment concertPayment = ConcertPayment.of(
                    null,
                    userId,
                    reservationId,
                    seatPrice,
                    paymentStatus,
                    paymentDate
            );

            concertPayments.add(concertPayment);
        }

        return concertPayments;
    }


    public void saveConcertPayments(List<ConcertPayment> concertPayments) {
        concertPaymentRepository.saveAll(concertPayments);
    }

    public List<ConcertSeat> getConcertSeatIdIn(Long[] concertSeatIds) {
        List<ConcertSeat> result = concertSeatRepository.getConcertSeatIdIn(concertSeatIds);
        if (result == null) {
            throw new IllegalArgumentException("좌석 정보가 없습니다.");
        }
        return result;
    }

    public void expiredWaitingQueue(String token) {
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueToken(token);
        waitingQueue.expire();
        waitingQueueRepository.save(waitingQueue);
    }

}
