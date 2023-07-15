package com.ably.demo.exception;

import lombok.Getter;

@Getter
public class RequestInvalidException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detail;

    public RequestInvalidException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.detail = errorCode.getMessage();
    }

    public RequestInvalidException(ErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }
}
