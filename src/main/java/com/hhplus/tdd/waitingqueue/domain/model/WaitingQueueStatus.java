package com.hhplus.tdd.waitingqueue.domain.model;

public enum WaitingQueueStatus {
    WAITING("대기 중"),
    ACTIVE("현재 활성화된 대기"),
    EXPIRED("대기 상태가 만료됨");

    private final String desc;

    WaitingQueueStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}