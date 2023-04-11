package com.h10.sideproject.profile.controller;

import com.h10.sideproject.common.response.MessageCode;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.profile.dto.ProfileRequestDto;
import com.h10.sideproject.profile.service.ProfileService;
import com.h10.sideproject.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {
    private final ProfileService profileService;
    // profile 에 있는 정보 수정
    @PutMapping("/profile")
    public ResponseMessage<?> updateProfile(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                            @RequestBody ProfileRequestDto profileRequestDto) {
        profileService.editProfile(memberDetails, profileRequestDto);
        return new ResponseMessage<>(MessageCode.PROFILE_UPDATE_SUCCESS, null);
    }
    //로그아웃 -> 토큰값 지우기
    @PostMapping("/profile/logout")
    public ResponseMessage<?> logOut(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        profileService.invalidateToken(memberDetails, request, response);
        return new ResponseMessage<>(MessageCode.MEMBER_LOGOUT_SUCCESS, null);
    }
    //회원탈퇴 -> 테이블에서 회원 찾아서 지우기
    @DeleteMapping("/profile/{memberId}")
    public ResponseMessage<?> outCommunity(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                           @PathVariable Long memberId) {
        profileService.withdrawal(memberId);
        return new ResponseMessage<>(MessageCode.MEMBER_DELETE_SUCCESS, null);
    }
}
