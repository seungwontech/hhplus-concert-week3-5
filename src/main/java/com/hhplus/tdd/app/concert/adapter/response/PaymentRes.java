package com.hhplus.tdd.app.concert.adapter.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentRes {
    private long paymentAmount;
    private String paymentStatus;
    private LocalDateTime paymentDate;
}
