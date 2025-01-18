package com.tech.springbaseproject.exception;

import com.tech.springbaseproject.common.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AppException.class)
    public ResponseEntity<BaseResponse<Object>> handleAppException(AppException ex) {
        log.error("AppException thrown: {}", ex.getMessage(), ex);

        // Use ex.getErrorCode() as HTTP status
        HttpStatus status = HttpStatus.valueOf(ex.getErrorCode());
        BaseResponse<Object> response = new BaseResponse<>(
                ex.getErrorCode(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, status);
    }

    // Catch any other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleAllOthers(Exception ex) {
        log.error("Unexpected exception occurred", ex);

        BaseResponse<Object> response = new BaseResponse<>(500, "Internal Server Error", null);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
