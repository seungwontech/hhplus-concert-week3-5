package com.hhplus.tdd.concert.presentation.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentRes {
    private int paymentAmount;
    private String paymentStatus;
    private LocalDateTime paymentDate;

    public static PaymentRes of(int paymentAmount, String paymentStatus, LocalDateTime paymentDate) {
        PaymentRes paymentRes = new PaymentRes();
        paymentRes.paymentAmount = paymentAmount;
        paymentRes.paymentStatus = paymentStatus;
        paymentRes.paymentDate = paymentDate;
        return paymentRes;
    }
}
