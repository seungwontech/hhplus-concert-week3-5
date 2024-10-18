package com.hhplus.tdd.concert.presentation.controller;

import com.hhplus.tdd.concert.application.usecase.ConcertPaymentUseCase;
import com.hhplus.tdd.concert.application.usecase.ConcertReservationUseCase;
import com.hhplus.tdd.concert.application.usecase.GetAvailableConcertDatesUseCase;
import com.hhplus.tdd.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import com.hhplus.tdd.concert.presentation.response.ConcertReservationRes;
import com.hhplus.tdd.concert.presentation.response.PaymentRes;
import com.hhplus.tdd.concert.presentation.response.ScheduleRes;
import com.hhplus.tdd.concert.presentation.response.SeatRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final GetAvailableConcertDatesUseCase getAvailableConcertDatesUseCase;
    private final GetAvailableSeatsUseCase getAvailableSeatsUseCase;
    private final ConcertReservationUseCase concertReservationUseCase;
    private final ConcertPaymentUseCase concertPaymentUseCase;

    // 예약 가능 날짜 조회 API
    @GetMapping("/{concertId}/schedules/")
    public ResponseEntity<ScheduleRes> concertSchedules(@PathVariable Long concertId) {
        ScheduleRes res = getAvailableConcertDatesUseCase.execute(concertId);
        return ResponseEntity.ok(res);
    }

    // 예약 가능 좌석 조회 API
    @GetMapping("/{concertId}/schedules/{concertScheduleId}/seats")
    public ResponseEntity<SeatRes> concertSeats(@PathVariable Long concertId, @PathVariable Long concertScheduleId) {
        SeatRes res = getAvailableSeatsUseCase.execute(concertId, concertScheduleId);
        return ResponseEntity.ok(res);
    }

    // 좌석 예약 API
    @PostMapping("/{concertId}/schedules/{concertScheduleId}/seats/reservation")
    public ResponseEntity<ConcertReservationRes> concertReservation(
            @RequestHeader("token") String token
            , @PathVariable Long concertId
            , @PathVariable Long concertScheduleId
            , @RequestBody ConcertReservationReq concertReservationReq) {
        ConcertReservationRes res = concertReservationUseCase.execute(concertId, concertScheduleId, concertReservationReq);
        return ResponseEntity.ok(res);
    }

    // 콘서트 결제 API
    @PostMapping("/{concertId}/schedules/{concertScheduleId}/seats/reservation/payment")
    public ResponseEntity<PaymentRes> concertPayment(
            @RequestHeader("Token") String token
            , @PathVariable Long concertId
            , @RequestBody ConcertPaymentReq concertPaymentReq) {
        PaymentRes res = concertPaymentUseCase.processConcertPayment(token, concertId, concertPaymentReq);

        return ResponseEntity.ok(res);

    }

}