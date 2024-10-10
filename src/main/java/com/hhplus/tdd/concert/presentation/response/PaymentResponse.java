package com.hhplus.tdd.concert.presentation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentResponse {
    private int amount;
    private LocalDateTime paidAt;
    private String status;
}
