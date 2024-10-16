package com.hhplus.tdd.app.concert.adapter.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueuePositionResponse {
    private Long queueToken;
    private int position;
}