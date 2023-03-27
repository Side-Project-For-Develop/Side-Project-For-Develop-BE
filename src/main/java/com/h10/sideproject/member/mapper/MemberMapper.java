package com.h10.sideproject.member.mapper;

import com.h10.sideproject.member.dto.SignupRequestDto;
import com.h10.sideproject.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapper {
    private final PasswordEncoder passwordEncoder;
    public Member toMember(SignupRequestDto signupRequestDto) {
        return Member.builder()
                .email(signupRequestDto.getEmail())
                .nickname(signupRequestDto.getNickname())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .memberId(signupRequestDto.getMemberId())
                .build();
    }
}
