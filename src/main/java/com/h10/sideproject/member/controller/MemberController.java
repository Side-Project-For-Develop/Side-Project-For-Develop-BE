package com.h10.sideproject.member.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.h10.sideproject.common.response.MessageCode;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.member.dto.*;
import com.h10.sideproject.member.service.KakaoService;
import com.h10.sideproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import com.h10.sideproject.security.jwt.JwtUtil;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    private final KakaoService kakaoService;


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

    //카카오 로그인
    @GetMapping("/member/kakao/callback")
    public String kakaoLogin(@RequestParam String code,
                             HttpServletResponse response) throws JsonProcessingException{
        String createToken = kakaoService.kakaoLogin(code, response);
        //bearer 글자 제외
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        //쿠키 생성한 서버의 모든 경로에서 해당 쿠키에 접근할 수 있도록 하는 것
        cookie.setPath("/");
        //HTTP 응답에 쿠키를 추가 -> 클라이언트로 보내 로그인 정보 유지
        response.addCookie(cookie);
        //로그인 후 해당 url 로 보내는 것
        return "redirect:/api/toks";
    }
}

