package com.ghkdtlwns987.apiserver.Global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // Member
    REGISTER_SUCCESS(200, "M001", "회원가입 되었습니다."),
    LOGIN_SUCCESS(200, "M001", "로그인 되었습니다."),
    REISSUE_SUCCESS(200, "M001", "재발급 되었습니다."),
    LOGOUT_SUCCESS(200, "M001", "로그아웃 되었습니다."),
    GET_MY_INFO_SUCCESS(200, "M001", "내 정보 조회 완료"),
    USER_UPDATE_SUCCESS(200, "M001", "회원정보 수정 완료"),
    MEMBER_NICKNAME_UPDATE_SUCCESS(200, "M001", "회원 NICKNAME 수정 완료"),
    MEMBER_PASSWORD_UPDATE_SUCCESS(200, "M001", "회원 PASSWORD 수정 완료"),
    MEMBER_WITHDRAWAL_SUCCESS(200, "M007", "회원탈퇴 완료"),
    MEMBER_INFORMATION_READ_SUCCESS(200, "M001", "회원 조회 완료"),

    CREATE_JOB_REQUEST_SUCCESS(200, "R001", "등록 되었습니다"),
    GET_JOB_STATUS_REQUEST_SUCCESS(200, "R002", "조회 되었습니다"),
    RUN_JOB_REQUEST_SUCCESS(200, "R003", "실행 되었습니다.");

    private int status;
    private final String code;
    private final String message;
}