package com.hhplus.tdd.concert.presentation.controller;

import com.hhplus.tdd.concert.application.usecase.GetAvailableConcertDatesUseCase;
import com.hhplus.tdd.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.tdd.concert.presentation.request.ConcertPaymentReq;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import com.hhplus.tdd.concert.presentation.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final GetAvailableConcertDatesUseCase getAvailableConcertDatesUseCase;
    private final GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    // 예약 가능 날짜 조회 API
    @GetMapping("/{concertId}/schedules/")
    public ResponseEntity<ScheduleRes> concertSchedules(@PathVariable Long concertId) {
        ScheduleRes res= getAvailableConcertDatesUseCase.execute(concertId);
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
            , @RequestBody ConcertReservationReq concertReservationReq){

        ConcertReservationRes res = ConcertReservationRes.builder()
                .userId(1L)
                .concertName("임영웅가을콘서트")
                .totalPrice(4000)
                .seats(List.of(
                        ConcertReservationRes.seat.builder()
                                .seatNumber(1)
                                .status("예약완료")
                                .seatPrice(2000)
                                .build(),
                        ConcertReservationRes.seat.builder()
                                .seatNumber(2)
                                .status("예약완료")
                                .seatPrice(2000)
                                .build()))
                .build();
        return ResponseEntity.ok(res);
    }

    // 콘서트 결제 API
    @PostMapping("/{concertId}/schedules/{concertScheduleId}/seats/reservation/payment")
    public ResponseEntity<PaymentRes> concertPayment (
            @RequestHeader("Token") String token
            , @PathVariable Long concertId
            , @RequestBody ConcertPaymentReq concertPaymentReq) {

        PaymentRes res = PaymentRes.builder()
                .paymentDate(LocalDateTime.now())
                .paymentAmount(3000)
                .paymentStatus("결제 완료")
                .build();

        return ResponseEntity.ok(res);

    }

}
