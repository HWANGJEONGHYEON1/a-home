package com.ably.demo.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detail;

    public UserNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.detail = errorCode.getMessage();
    }
}
