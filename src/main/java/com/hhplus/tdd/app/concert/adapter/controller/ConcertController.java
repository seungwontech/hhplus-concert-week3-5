package com.hhplus.tdd.app.concert.adapter.controller;

import com.hhplus.tdd.app.concert.adapter.request.ConcertPaymentReq;
import com.hhplus.tdd.app.concert.adapter.request.ConcertReservationReq;
import com.hhplus.tdd.app.concert.adapter.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concerts")
public class ConcertController {


    // 예약 가능 날짜 조회 API
    @GetMapping("/{concertId}/schedules/")
    public ResponseEntity<ScheduleRes> concertSchedules(@PathVariable long concertId) {
        ScheduleRes res = ScheduleRes.builder()
                .concertId(concertId)
                .schedules(List.of(
                        ScheduleRes.schedule.builder()
                                .concertDateId(1L)
                                .scheduleAt(LocalDateTime.parse("2024-10-10T10:00:00"))
                                .build(),
                        ScheduleRes.schedule.builder()
                                .concertDateId(2L)
                                .scheduleAt(LocalDateTime.parse("2024-10-10T10:10:10"))
                                .build()
                )).build();
        return ResponseEntity.ok(res);
    }

    // 예약 가능 좌석 조회 API
    @GetMapping("/{concertId}/schedules/{concertScheduleId}/seats")
    public ResponseEntity<SeatRes> concertSeats(@PathVariable long concertId, @PathVariable long concertScheduleId) {
        SeatRes res = SeatRes.builder()
                .concertId(concertId)
                .scheduleId(concertScheduleId)
                .seats(List.of(
                        SeatRes.seat.builder().seatId(1L).seatNumber(1).reserveYn("N").build(),
                        SeatRes.seat.builder().seatId(1L).seatNumber(2).reserveYn("N").build()
                ))
                .build();

        return ResponseEntity.ok(res);
    }

    // 좌석 예약 API
    @PostMapping("/{concertId}/schedules/{concertScheduleId}/seats/reservation")
    public ResponseEntity<ConcertReservationRes> concertReservation(
            @RequestHeader("token") String token
            , @PathVariable long concertId
            , @PathVariable long concertScheduleId
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
            , @PathVariable long concertId
            , @RequestBody ConcertPaymentReq concertPaymentReq) {

        PaymentRes res = PaymentRes.builder()
                .paymentDate(LocalDateTime.now())
                .paymentAmount(3000)
                .paymentStatus("결제 완료")
                .build();

        return ResponseEntity.ok(res);

    }

}
