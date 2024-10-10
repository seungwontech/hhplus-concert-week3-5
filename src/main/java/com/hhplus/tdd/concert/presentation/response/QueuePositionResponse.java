package com.hhplus.tdd.concert.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueuePositionResponse {
    private Long queueToken;
    private int position;
}