package org.example.backend.authentication.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.authentication.services.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    @Value("${redis.prefix.blacklist}")
    private String blacklistPrefix;

    private final StringRedisTemplate redisTemplate;

    @Override
    public void blacklistToken(String jti, Instant expireTime) {
        var now = Instant.now();

        var remainingSeconds = Duration.between(now, expireTime).getSeconds();

        if (remainingSeconds <= 0) {
            return;
        }

        redisTemplate.opsForValue().set(
                blacklistPrefix + jti,
                "logged_out",
                remainingSeconds,
                TimeUnit.SECONDS
        );
    }

    @Override
    public Boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(blacklistPrefix + jti));
    }
}
