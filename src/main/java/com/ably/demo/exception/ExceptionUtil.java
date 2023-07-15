package com.ably.demo.exception;

import com.ably.demo.common.ResponseError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtil {
    public static ResponseError fieldError(FieldError fieldError) {
        if (ObjectUtils.isEmpty(fieldError) || !StringUtils.hasText(fieldError.getCode())) {
            return ResponseError.ofErrorCode(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        String codeType = fieldError.getCode();
        String errorDefMsg = fieldError.getDefaultMessage();
        Object[] errorArguments = fieldError.getArguments();

        Object errorArguments1 = errorArguments.length > 1 ? errorArguments[1] : "";
        Object errorArguments2 = errorArguments.length > 2 ? errorArguments[2] : "";

        StringBuilder errorMessage = new StringBuilder();
        ErrorCode errorCode;

        switch (codeType) {
            case "NotNull":
            case "NotEmpty":
            case "NotBlank":
                errorCode = ErrorCode.PARAM_INVALID_ERROR_E002;
                errorMessage.append("(").append(errorDefMsg).append(")").append(ErrorCode.PARAM_INVALID_ERROR_E002.getMessage());
                break;
            case "Size":
                if (!ObjectUtils.isEmpty(errorArguments2) && (int) errorArguments2 > 0) {
                    errorCode = ErrorCode.PARAM_INVALID_ERROR_E003;

                    errorMessage.append("(").append(errorDefMsg).append(")");
                    errorMessage.append(errorArguments2).append("~").append(errorArguments1).append(ErrorCode.PARAM_INVALID_ERROR_E003.getMessage());
                } else {
                    errorCode = ErrorCode.PARAM_INVALID_ERROR_E004;

                    errorMessage.append("(").append(errorDefMsg).append(")");
                    errorMessage.append(errorArguments1).append(ErrorCode.PARAM_INVALID_ERROR_E004.getMessage());
                }
                break;
            case "Pattern":
                errorCode = ErrorCode.PARAM_INVALID_ERROR_E005;
                errorMessage.append("(").append(errorDefMsg).append(")").append(ErrorCode.PARAM_INVALID_ERROR_E005.getMessage());
                break;
            default:
                errorCode = ErrorCode.PARAM_INVALID_ERROR_E001;
                errorMessage.append("(").append(errorDefMsg).append(")").append(ErrorCode.PARAM_INVALID_ERROR_E001.getMessage());
                break;
        }

        return ResponseError.builder()
                .code(errorCode.getCode())
                .title(errorCode.name())
                .detail(errorMessage.toString())
                .build();
    }

}
