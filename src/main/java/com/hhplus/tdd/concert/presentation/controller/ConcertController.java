package com.hhplus.tdd.concert.presentation.controller;

import com.hhplus.tdd.concert.presentation.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concert")
public class ConcertController {

    /**
     * 유저 토큰 발급 API
     *
     * @param userId
     * @return
     */
    @PostMapping("/token/{userId}")
    public ResponseEntity<TokenIssueResponse> issueToken(
            @PathVariable Long userId) {
        TokenIssueResponse response = TokenIssueResponse.builder()
                .userId(1L)
                .token("a1b2c3")
                .expiryTime(LocalDateTime.now().plusMinutes(5L))
                .createdAt(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 토큰을 조회
     *
     * @param userId
     * @return
     */
    @GetMapping("/queue/token")
    public ResponseEntity<TokenResponse> getToken(
            @RequestParam Long userId) {
        TokenResponse response = TokenResponse.builder()
                .userId(1L)
                .token("a1b2c3")
                .expiryTime(LocalDateTime.now().plusMinutes(5L))
                .createdAt(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 대기열 순번 조회
     *
     * @param queueTokenId
     * @return
     */
    @GetMapping("/queue/position")
    public ResponseEntity<QueuePositionResponse> getQueuePosition(
            @RequestParam Long queueTokenId) {

        QueuePositionResponse response = QueuePositionResponse.builder()
                .queueToken(queueTokenId)
                .position(40)
                .build();

        return ResponseEntity.ok(response);
    }


    /**
     * 예약 가능 날짜
     *
     * @param token
     * @param concertId
     * @return
     */
    @GetMapping("/{concertId}/schedule/")
    public ResponseEntity<ScheduleResponse> getConcertSchedules(
            @RequestHeader("TOKEN") String token
            , @PathVariable Long concertId) {
        ScheduleResponse response = ScheduleResponse.builder()
                .concertId(concertId)
                .concertSchedules(List.of(
                        ScheduleResponse.concertSchedule.builder()
                                .concertDateId(1L)
                                .scheduleAt(LocalDateTime.parse("2024-10-10T10:00:00"))
                                .build(),
                        ScheduleResponse.concertSchedule.builder()
                                .concertDateId(2L)
                                .scheduleAt(LocalDateTime.parse("2024-10-10T10:10:10"))
                                .build()
                ))
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 예약 가능 좌석
     *
     * @param token
     * @param concertId
     * @return
     */
    @GetMapping("/{concertId}/schedule/{concertDateId}/seat")
    public ResponseEntity<SeatResponse> getConcertSeats(
            @RequestHeader("TOKEN") String token
            , @PathVariable Long concertId
            , @PathVariable Long concertDateId) {
        SeatResponse response = SeatResponse.builder()
                .concertId(concertId)
                .concertDateId(concertId)
                .concertSeats(List.of(
                        SeatResponse.concertSeat.builder()
                                .seatId(1L)
                                .seatNumber(1)
                                .reserveYn("N")
                                .build(),
                        SeatResponse.concertSeat.builder()
                                .seatId(1L)
                                .seatNumber(2)
                                .reserveYn("N")
                                .build()
                ))
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 좌석 예약
     *
     * @param token
     * @param userId
     * @param concertDateId
     * @param seatIds
     * @return
     */
    @PostMapping("/reservations")
    public ResponseEntity<SeatReservationResponse> createSeatReservation(
            @RequestHeader("TOKEN") String token
            , @RequestParam Long userId
            , @RequestParam Long concertDateId
            , @RequestParam Long[] seatIds) {
        SeatReservationResponse response = SeatReservationResponse.builder()
                .userId(1L)
                .concertName("임영웅가을콘서트")
                .totalPrice(4000)
                .seats(List.of(
                        SeatReservationResponse.seat.builder()
                                .seatNumber(1)
                                .status("예약완료")
                                .seatPrice(2000)
                                .build(),
                        SeatReservationResponse.seat.builder()
                                .seatNumber(2)
                                .status("예약완료")
                                .seatPrice(2000)
                                .build()))
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 결제
     *
     * @param token
     * @param userId
     * @return
     */
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> payment(
            @RequestHeader("TOKEN") String token
            , @RequestParam Long userId
            , @RequestParam Long reservationId) {
        PaymentResponse response = PaymentResponse.builder()
                .paidAt(LocalDateTime.now())
                .amount(3000)
                .status("결제 완료")
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 잔액 조회
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/balance/")
    public ResponseEntity<BalanceResponse> getBalance(@RequestParam Long userId) {
        BalanceResponse response = BalanceResponse.builder()
                .userId(userId)
                .amount(3000)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 잔액 충전
     *
     * @param userId
     * @return
     */
    @PatchMapping("/user/balance/charge/{userId}")
    public ResponseEntity<BalanceResponse> chargeBalance(@PathVariable Long userId) {
        BalanceResponse response = BalanceResponse.builder()
                .userId(userId)
                .amount(3000)
                .build();

        return ResponseEntity.ok(response);
    }
}
