package com.h10.sideproject.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileRequestDto {
    private String nickname;
    private String profileImage;
}
