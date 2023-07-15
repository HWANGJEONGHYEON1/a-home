package com.ably.demo.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detail;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.detail = errorCode.getMessage();
    }
}
