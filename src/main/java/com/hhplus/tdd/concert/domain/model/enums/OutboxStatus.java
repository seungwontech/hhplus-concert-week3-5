package com.hhplus.tdd.concert.domain.model.enums;

public enum OutboxStatus {
    WAITING("대기")
    , PUBLISHED("발행 완료");

    private final String value;

    OutboxStatus(String value) {
        this.value = value;
    }
}
