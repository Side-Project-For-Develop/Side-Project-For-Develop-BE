package com.h10.sideproject.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageCode {

    //작업 중 당장 반환값이 없을 경우 임의로 사용
    SUCCESS("성공", 200),
    //로그인, 회원가입, 소셜로그인, 토큰
    MEMBER_LOGIN_SUCCESS("로그인 성공", 200),
    MEMBER_SIGNUP_SUCCESS("회원가입 성공", 200),

    //이미지
    IMAGE_UPLOAD_SUCCESS("이미지 업로드 성공", 200),
    //메인
    
    //리스트페이지
    
    //상세페이지
    POLL_WRITE_SUCCESS("설문 작성 완료",200),
    
    //댓글
    COMMEMT_CREATE_SUCCESS("댓글 작성 성공", 200),
    COMMENT_CHECK_SUCCESS("특정 게시글의 댓글 조회 성공", 200),
    COMMENT_UPDATE_SUCCESS("댓글 수정 성공", 200),
    COMMENT_DELETE_SUCCESS("댓글 삭제 성공", 200),
    //좋아요
    
    //프로필
    PROFILE_UPDATE_SUCCESS("프로필 수정 성공", 200),
    MEMBER_LOGOUT_SUCCESS("로그아웃 성공", 200),
    MEMBER_DELETE_SUCCESS("회원탈퇴 성공", 200);
    //토큰 관련
    private final String msg;
    private final int statusCode;

}
