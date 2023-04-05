package com.h10.sideproject.refreshToken.controller;

import com.h10.sideproject.common.ResponseMessage;
import com.h10.sideproject.refreshToken.dto.RefreshTokenRequest;
import com.h10.sideproject.refreshToken.dto.RefreshTokenResponse;
import com.h10.sideproject.refreshToken.service.RefreshTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.h10.sideproject.common.MessageCode.TOKEN_RETURN_SUCCESS;

@RequestMapping("/api")
@RestController
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(final RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }
    //리프레쉬 토큰 생성 완료 처리
    @PostMapping("/refresh_token")
    public ResponseMessage<?> generateRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenResponse refreshTokenResponse = refreshTokenService.createRefreshToken(refreshTokenRequest);
        return new ResponseMessage<>(TOKEN_RETURN_SUCCESS, refreshTokenResponse);
    }
}
