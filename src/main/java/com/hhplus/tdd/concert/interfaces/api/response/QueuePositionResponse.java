package com.hhplus.tdd.concert.interfaces.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueuePositionResponse {
    private Long queueToken;
    private int position;
}