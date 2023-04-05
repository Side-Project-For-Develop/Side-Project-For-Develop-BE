package com.h10.sideproject.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //400 BAD_REQUEST 잘못된 요청
    DUPLICATE_MEMBERID("중복된 아이디가 존재합니다.", 400),
    DUPLICATE_EMAIL("중복된 이메일이 존재합니다.", 400),
    DUPLICATE_NICKNAME("중복된 닉네임이 존재합니다.", 400),
    MEMBER_FOUND_NULL("유저가 없습니다.", 400),
    INVAILD_ACCESS_TOKEN("잘못된 액세스 토큰", 400),


    //NOT_FOUND 404
    NOT_FOUND_MEMBER("해당 유저가 없습니다", 404),
    NOT_FOUND_EMAIL("해당 email 이 없습니다.", 404),
    NOT_FOUND_COMMENT("해당 댓글이 없습니다.", 404),
    NOT_FOUND_ID("해당 유저의 아이디가 없습니다", 404),


    //필터단 에러
    FORBIDDEN_ERROR("서버 사용 권한이 없습니다.",403),
    TOKEN_ERROR("토큰이 유효하지 않습니다.",401),
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다. 로그인이 필요합니다.",401),
    MEMBER_NOT_FOUND("존재하지 않는 멤버 입니다.",404),
    INCORRECT_PASSWORD("잘못된 비밀번호입니다.",404),
    INVALID_TOKEN("잘못된 토큰", 404),

    //jwt
    DO_NOT_HAVE_PERMISSION_ERROR_MSG("사용 권한이 없습니다.", 403),
    
    //댓글 관련 에러
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다.", 404),
    POLL_NOT_FOUND("투표 상세페이지를 찾을 수 없습니다.", 404),
    
    //401 : 권한 관련
    AUTHORIZATION_CANNOT_CREATE("작성 권한이 없습니다", 401),
    AUTHORIZATION_CANNOT_UPDATE("수정 권한이 없습니다", 401),
    AUTHORIZATION_CANNOT_DELETE("삭제 권한이 없습니다", 401);


    private final String msg;
    private final int statusCode;
}