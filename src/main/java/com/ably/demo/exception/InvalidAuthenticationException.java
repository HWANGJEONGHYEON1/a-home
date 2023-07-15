package com.ably.demo.exception;

import lombok.Getter;

@Getter
public class InvalidAuthenticationException extends RuntimeException {
    private ErrorCode errorCode;
    private String detail;

    public InvalidAuthenticationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.detail = errorCode.getMessage();
    }
}
