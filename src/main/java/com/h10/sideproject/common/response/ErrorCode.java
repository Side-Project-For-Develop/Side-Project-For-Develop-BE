package com.h10.sideproject.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //400 BAD_REQUEST 잘못된 요청
    DUPLICATE_MEMBERID("중복된 아이디가 존재합니다.", 400),
    DUPLICATE_EMAIL("중복된 이메일이 존재합니다.", 400),
    DUPLICATE_NICKNAME("중복된 닉네임이 존재합니다.", 400),


    //필터단 에러
    FORBIDDEN_ERROR("서버 사용 권한이 없습니다.",403),
    TOKEN_ERROR("토큰이 유효하지 않습니다.",401),
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다. 로그인이 필요합니다.",401),
    MEMBER_NOT_FOUND("존재하지 않는 멤버 입니다.",404),
    INCORRECT_PASSWORD("잘못된 비밀번호입니다.",404),


    //jwt
    DO_NOT_HAVE_PERMISSION_ERROR_MSG("사용 권한이 없습니다.", 403),

    //설문
    POLL_REQUIRED_NOT_ENOUGH("필수항목을 입력해 주세요", HttpStatus.BAD_REQUEST.value()),

    POLL_NOT_FOUND("존재하지 않는 설문입니다.", HttpStatus.BAD_REQUEST.value());


    private final String msg;
    private final int statusCode;
}
