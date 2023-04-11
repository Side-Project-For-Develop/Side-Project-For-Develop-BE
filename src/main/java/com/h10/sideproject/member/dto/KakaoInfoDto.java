package com.h10.sideproject.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoInfoDto {
    // 카카오에서 받아온 아이디
    private Long kakaoId;
    private String email;
    private String nickname;
    private String profileImage;

    public KakaoInfoDto(Long kakaoId, String nickname, String email, String profileImage) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }
}
