package com.homework.demo.exception;

import com.homework.demo.common.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseObject<Object> defaultExceptionHandler(Exception e) {
        log.error("defaultExceptionHandler : {}", ExceptionUtils.getStackTrace(e));
        return ResponseObject.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject<Object> webExchangeBindExceptionHandler(MethodArgumentNotValidException e) {
        return ResponseObject.fieldError(e.getFieldError());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject<Object> businessExceptionHandler(BusinessException e) {
        log.warn("businessExceptionHandler : {}", ExceptionUtils.getStackTrace(e));
        if (StringUtils.hasText(e.getDetail())) {
            return ResponseObject.error(e.getErrorCode(), e.getDetail());
        }
        return ResponseObject.error(e.getErrorCode());
    }

    @ExceptionHandler(RequestInvalidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseObject<Object> requestExceptionHandler(RequestInvalidException e) {
        log.warn("requestExceptionHandler : {}", ExceptionUtils.getStackTrace(e));
        if (StringUtils.hasText(e.getDetail())) {
            return ResponseObject.error(e.getErrorCode(), e.getDetail());
        }
        return ResponseObject.error(e.getErrorCode());
    }

    @ExceptionHandler(InvalidAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseObject<Object> invalidExceptionHandler(InvalidAuthenticationException e) {
        log.warn("invalidExceptionHandler : {}", ExceptionUtils.getStackTrace(e));
        if (StringUtils.hasText(e.getDetail())) {
            return ResponseObject.error(e.getErrorCode(), e.getDetail());
        }
        return ResponseObject.error(e.getErrorCode());
    }
}
