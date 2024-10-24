package com.hhplus.tdd.config.exception;

import lombok.Getter;

@Getter
public record ErrorResponse(ErrorCode errorCode, String message, Object payload) {
}
