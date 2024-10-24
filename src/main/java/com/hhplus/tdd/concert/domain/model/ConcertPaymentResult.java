package com.hhplus.tdd.concert.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ConcertPaymentResult {
    private int totalPrice;         // 결제 총액
    private String paymentStatus;       // 결제 상태
    private LocalDateTime timestamp;    // 타임스탬프

    public static ConcertPaymentResult of(int totalPrice) {
        return new ConcertPaymentResult(totalPrice, PaymentStatus.SUCCESS.toString() , LocalDateTime.now());
    }
}
