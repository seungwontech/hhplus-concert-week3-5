package com.hhplus.tdd.config.exception;

public record ErrorResponse(ErrorCode errorCode, String message, Object payload) {
}
