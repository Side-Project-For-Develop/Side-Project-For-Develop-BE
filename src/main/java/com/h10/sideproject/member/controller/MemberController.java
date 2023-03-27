package com.h10.sideproject.member.controller;

import com.h10.sideproject.common.MessageCode;
import com.h10.sideproject.common.ResponseMessage;
import com.h10.sideproject.member.dto.*;
import com.h10.sideproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseMessage<?> signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        memberService.signup(signupRequestDto);
        return new ResponseMessage<>(MessageCode.MEMBER_SIGNUP_SUCCESS, null);
    }

    @PostMapping("/member/login")
    public ResponseMessage<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        memberService.login(loginRequestDto, response);
        return new ResponseMessage<>(MessageCode.MEMBER_LOGIN_SUCCESS, null);
    }
    @PostMapping("/member/checkid")
    public ResponseMessage<?> memberIdcheck(@RequestBody MemberIdCheckDto memberIdcheckDto){
        memberService.memberidcheck(memberIdcheckDto);
        return new ResponseMessage<>("Success", 200, null);
    }

    @PostMapping("/member/checkemail")
    public ResponseMessage<?> emailcheck(@RequestBody EmailCheckDto emailcheckDto){
        memberService.emailcheck(emailcheckDto);
        return new ResponseMessage<>("Success", 200, null);
    }

    @PostMapping("/member/checknickname")
    public ResponseMessage<?> nicknamecheck(@RequestBody NicknameCheckDto nicknamecheckDto){
        memberService.nicknamecheck(nicknamecheckDto);
        return new ResponseMessage<>("Success", 200, null);
    }
}
