package com.hhplus.tdd.concert.presentation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentRes {
    private int paymentAmount;
    private String paymentStatus;
    private LocalDateTime paymentDate;
}
