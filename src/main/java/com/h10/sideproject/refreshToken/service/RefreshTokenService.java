package com.h10.sideproject.refreshToken.service;

import com.h10.sideproject.common.exception.CustomException;
import com.h10.sideproject.member.repository.MemberRepository;
import com.h10.sideproject.refreshToken.dto.RefreshTokenRequest;
import com.h10.sideproject.refreshToken.dto.RefreshTokenResponse;
import com.h10.sideproject.refreshToken.entity.RefreshToken;
import com.h10.sideproject.refreshToken.repository.RefreshTokenRepository;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.h10.sideproject.common.exception.ErrorCode.NOT_FOUND_EMAIL;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final String SECRET_KEY = "abcdefgabcdefgabcdefgabcdefgabcdefgabcdefg";
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SecretKey secretKey;
    @Autowired
    public RefreshTokenService(MemberRepository memberRepository,
                               RefreshTokenRepository refreshTokenRepository) {
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    //예외 처리 -> findByEmailAndPassword 메소드 사용 후 이메일과 패스워드 없을 때 예외처리
    public RefreshTokenResponse createRefreshToken(RefreshTokenRequest request) {
        //memberId 찾기
        Long Id = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new CustomException(NOT_FOUND_EMAIL))
                .getId();
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), Id);
        refreshTokenRepository.save(refreshToken);

        return new RefreshTokenResponse(refreshToken.getToken());
    }
}