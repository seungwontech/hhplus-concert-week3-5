package com.hhplus.tdd.concert.domain.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ConcertPayment {
    private Long paymentId; // 결제 ID
    private Long userId; // 사용자 ID
    private Long concertReservationId; // 콘서트 예약 ID
    private int paymentAmount; // 결제 금액
    private String paymentStatus; // 결제 상태
    private LocalDateTime paymentDate; // 결제 날짜

    public static ConcertPayment of(Long paymentId, Long userId, Long concertReservationId, int paymentAmount, String paymentStatus, LocalDateTime paymentDate) {
        return new ConcertPayment(paymentId, userId, concertReservationId, paymentAmount, paymentStatus, paymentDate);
    }
}