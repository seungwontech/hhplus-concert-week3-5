package com.hhplus.tdd.concert.domain.model.enums;

public enum PaymentStatus {
    FAILED("결제 실패"),
    SUCCESS("결제 성공");

    private final String desc;

    PaymentStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}