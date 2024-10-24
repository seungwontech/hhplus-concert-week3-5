package com.hhplus.tdd.concert.domain.model;

public enum ReservationStatus {
    WAITING("예약 대기 중"),
    CONFIRMED("예약 확인됨"),
    CANCELED("예약 취소됨");

    private final String desc;

    ReservationStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}