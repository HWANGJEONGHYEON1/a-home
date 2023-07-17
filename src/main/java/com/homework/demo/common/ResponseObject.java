package com.homework.demo.common;

import com.homework.demo.exception.ErrorCode;
import com.homework.demo.exception.ExceptionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseObject<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private List<ResponseError> errors;

    public static <T> ResponseObject<T> toResponse(T data) {
        return ResponseObject.<T>builder()
                .data(data)
                .build();
    }

    public static ResponseObject<Object> fieldError(FieldError fieldError) {
        return ResponseObject.builder()
                .errors(Collections.singletonList(ExceptionUtil.fieldError(fieldError)))
                .build();
    }

    public static ResponseObject<Object> error(ErrorCode errorCode) {
        return ResponseObject.builder()
                .errors(Collections.singletonList(ResponseError.ofErrorCode(errorCode)))
                .build();
    }

    public static ResponseObject<Object> error(ErrorCode errorCode, String detail) {
        return ResponseObject.builder()
                .errors(Collections.singletonList(ResponseError.builder()
                        .code(errorCode.getCode())
                        .title(errorCode.name())
                        .detail(detail)
                        .build()))
                .build();
    }
}
