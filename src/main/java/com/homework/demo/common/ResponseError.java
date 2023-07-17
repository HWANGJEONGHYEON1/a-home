package com.homework.demo.common;

import com.homework.demo.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseError {

    private String code;

    private String title;

    private String detail;

    public static ResponseError ofErrorCode(ErrorCode errorCode) {
        return ResponseError.builder()
                .code(errorCode.getCode())
                .title(errorCode.name())
                .detail(errorCode.getMessage())
                .build();
    }
}
