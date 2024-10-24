package com.hhplus.tdd.config;

import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorCode;
import com.hhplus.tdd.config.exception.ErrorResponse;
import com.hhplus.tdd.config.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * CoreException을 처리하는 메서드
     */
    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreException coreException) {
        logError(coreException.getErrorType());

        ErrorResponse errorResponse = new ErrorResponse(
                coreException.getErrorType().getErrorCode()
                , coreException.getErrorType().getMessage()
                , coreException.getPayload()
        );
        HttpStatus status = mapToHttpStatus(coreException.getErrorType().getErrorCode());
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * 그 외의 모든 예외를 처리하는 메서드
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception exception) {
        log.warn("Exception occur: ", exception);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.DB_ERROR, "서버 내부 오류가 발생했습니다.", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 에러 타입에 따라 HTTP 상태 코드 매핑
     */
    private HttpStatus mapToHttpStatus(ErrorCode errorCode) {
        switch (errorCode) {
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case CLIENT_ERROR:
                return HttpStatus.BAD_REQUEST;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * 에러 로그 기록
     */
    private void logError(ErrorType errorType) {
        LogLevel logLevel = errorType.getLogLevel();
        switch (logLevel) {
            case WARN:
                log.warn(errorType.getMessage());
                break;
            case ERROR:
                log.error(errorType.getMessage());
                break;
            default:
                log.info(errorType.getMessage());
                break;
        }
    }
}