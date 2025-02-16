package com.tech.springbaseproject.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final int errorCode;

    public AppException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
