package org.example.backend.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final Algorithm algorithm;

    @Value("${jwt.access-token-expiration-minutes}")
    private Integer accessTokenExpirationTime;

    @Value("${jwt.refresh-token-expiration-minutes}")
    private Integer refreshTokenExpirationTime;

    public JwtTokenPair generateAccessAndRefreshTokens(Authentication authentication) {
        String accessToken = generateToken(authentication, accessTokenExpirationTime);
        String refreshToken = generateToken(authentication, refreshTokenExpirationTime);
        return JwtTokenPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateToken(Authentication authentication, Integer ttl) {
        return JWT.create()
                .withSubject(authentication.getName())
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(ttl, ChronoUnit.MINUTES))
                .withClaim("roles", authentication.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }
}
