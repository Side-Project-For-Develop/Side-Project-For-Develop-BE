package com.h10.sideproject.member.service;

import com.h10.sideproject.common.exception.CustomException;
import com.h10.sideproject.common.response.ErrorCode;
import com.h10.sideproject.common.response.MessageCode;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.member.dto.EmailCheckDto;
import com.h10.sideproject.member.dto.LoginRequestDto;
import com.h10.sideproject.member.dto.NicknameCheckDto;
import com.h10.sideproject.member.dto.SignupRequestDto;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.member.mapper.MemberMapper;
import com.h10.sideproject.member.repository.MemberRepository;
import com.h10.sideproject.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.h10.sideproject.common.response.ErrorCode.INCORRECT_PASSWORD;
import static com.h10.sideproject.common.response.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    public synchronized void signup(SignupRequestDto signupRequestDto) {
        Member member = memberMapper.toMember(signupRequestDto);

        Optional<Member> checkEmail = memberRepository.findByEmail(member.getEmail());
        if(checkEmail.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        Optional<Member> checkNickname = memberRepository.findByNickname(member.getNickname());
        if(checkNickname.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        memberRepository.save(member);
    }
    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );
        //비밀번호 확인
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())){
            throw new CustomException(INCORRECT_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getEmail()));
    }

    public ResponseMessage<?> emailcheck(EmailCheckDto emailcheckDto) {
        Optional<Member> checkEmail = memberRepository.findByEmail(emailcheckDto.getEmail());
        if(checkEmail.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }else{
            return new ResponseMessage<>(MessageCode.EMAIL_SUCCESS, null);
        }
    }

    public ResponseMessage<?> nicknamecheck(NicknameCheckDto nicknamecheckDto) {
        Optional<Member> checkNickname = memberRepository.findByNickname(nicknamecheckDto.getNickname());
        if(checkNickname.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }else{
           return new ResponseMessage<>(MessageCode.NICKNAME_SUCCESS, null);
        }
    }
}
