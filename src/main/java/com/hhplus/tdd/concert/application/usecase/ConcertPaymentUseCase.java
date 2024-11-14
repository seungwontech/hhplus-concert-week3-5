package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.balance.domain.model.Balance;
import com.hhplus.tdd.balance.domain.repository.BalanceRepository;
import com.hhplus.tdd.concert.ConcertEvent;
import com.hhplus.tdd.concert.ConcertEventPublisher;
import com.hhplus.tdd.concert.domain.model.*;
import com.hhplus.tdd.concert.domain.repository.ConcertPaymentRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertReservationRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConcertPaymentUseCase {

    private final WaitingQueueRepository waitingQueueRepository;

    private final ConcertPaymentRepository concertPaymentRepository;

    private final ConcertSeatRepository concertSeatRepository;

    private final ConcertReservationRepository concertReservationRepository;

    private final BalanceRepository balanceRepository;

    private final ConcertEventPublisher concertEventPublisher;

    @Transactional
    public ConcertPaymentResult execute(String token, ConcertPaymentReq concertPaymentReq) {

        List<ConcertSeat> concertSeats = concertSeatRepository.getConcertSeatIdIn(concertPaymentReq.getConcertSeatIds());

        if (concertSeats.isEmpty()) {
            throw new CoreException(ErrorType.CONCERT_SEAT_NOT_FOUND, concertSeats);
        }

        List<ConcertPayment> concertPaymenteds = concertPaymentRepository.findByUserIdAndConcertReservationIdIn(concertPaymentReq.getUserId(),concertPaymentReq.getConcertReservationId());

        if (concertPaymenteds != null) {
            throw new CoreException(ErrorType.DUPLICATE_CONCERT_PAYMENTS, concertPaymenteds);
        }

        List<ConcertPayment> concertPayments = processConcertPayment(concertPaymentReq, concertSeats);
        saveConcertPayments(concertPayments);

        List<ConcertReservation> concertReservation = concertReservationRepository.findByConcertReservationIdIn(concertPaymentReq.getConcertReservationId());
        concertReservation.forEach(ConcertReservation::setReservationStatus);
        concertReservationRepository.saveAll(concertReservation);

        int totalPrice = concertPayments.stream().mapToInt(ConcertPayment::getPaymentAmount).sum();
        ConcertPaymentResult result = ConcertPaymentResult.of(totalPrice);

        Map<String, Object> eventData = Map.of(
                "token", token,
                "userId", concertPaymentReq.getUserId(),
                "totalPrice", totalPrice
        );

        concertEventPublisher.notifyComplete(ConcertEvent.toCompleteEvent(eventData));

        return result;
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
            int seatPrice = reservedSeatPrices.get(seatId); // 좌석 가격 가져오기

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


    public void expiredWaitingQueue(String token) {
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueToken(token);
        waitingQueue.expire();
        waitingQueueRepository.save(waitingQueue);
    }


    public void deductUserPoints(int totalPrice, Long userId) {
        Balance balance = balanceRepository.getBalance(userId);

        if (balance == null ) {
            throw new CoreException(ErrorType.BALANCE_NOT_FOUND, userId);
        }

        Balance updatedBalance = balance.use(totalPrice);
        balanceRepository.save(updatedBalance);

        balanceRepository.save(balance);
    }
}

