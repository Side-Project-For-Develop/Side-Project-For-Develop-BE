package com.h10.sideproject.refreshToken.repository;

import com.h10.sideproject.refreshToken.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate redisTemplate;
    //redis 에 RefreshToken 저장
    public void save(RefreshToken refreshToken) {
        //Redis 에서 값을 읽고 쓰는 데 사용되는 인터페이스
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getToken(), refreshToken.getId());
        //만료 시간 정해주는 역할
        redisTemplate.expire(refreshToken.getToken(), 60L, TimeUnit.SECONDS);
    }
    //redis 에서 RefreshToken 조회
    public Optional<RefreshToken> findById(String refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long Id = valueOperations.get(refreshToken);

        if (Objects.isNull(Id)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(refreshToken, Id));
    }
}
