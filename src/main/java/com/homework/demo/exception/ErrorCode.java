package com.homework.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 파라미터 요청 공통 오류
     */
    PARAM_INVALID_ERROR_E001("E001", " 파라미터 유효성 검사 오류입니다."),
    PARAM_INVALID_ERROR_E002("E002", " 필수값을 입력해주세요."),
    PARAM_INVALID_ERROR_E003("E003", " 자 사이로 입력해주세요."),
    PARAM_INVALID_ERROR_E004("E004", " 자 이하로 입력해주세요."),
    PARAM_INVALID_ERROR_E005("E005", " 형식에 맞지 않습니다."),

    REQUEST_INVALID_R001("R001", "잘못된 요청입니다."),
    REQUEST_INVALID_R002("R002", "상품이 존재하지 없습니다."),
    REQUEST_INVALID_R003("R003", "회원이 존재하지 않습니다."),
    REQUEST_INVALID_R004("R004", "존재하는 서랍이 없습니다. 먼저 만들어주세요."),
    REQUEST_INVALID_R005("R005", "서랍의 이름이 존재합니다."),
    REQUEST_INVALID_R006("R006", "다른 서랍에 상품이 존재합니다."),
    REQUEST_INVALID_R007("R007", "존재하지 않는 찜입니다."),
    REQUEST_INVALID_R008("R008", "이미 존재하는 이메일입니다."),
    REQUEST_INVALID_R009("R009", "아이디 또는 패스워드가 틀렸습니다."),
    AUTHENTICATION_FAIL("A001", "권한이 없습니다"),
    INTERNAL_SERVER_ERROR("9001", "시스템 에러 발생: 관리자에게 문의 바랍니다.");

    private final String code;

    private final String message;
}
