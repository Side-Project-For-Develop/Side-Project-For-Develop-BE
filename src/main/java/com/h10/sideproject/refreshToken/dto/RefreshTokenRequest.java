package com.h10.sideproject.refreshToken.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {
    private String refreshToken;
    private String email;
    private String Password;
}
