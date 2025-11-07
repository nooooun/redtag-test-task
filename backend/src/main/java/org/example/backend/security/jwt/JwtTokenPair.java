package org.example.backend.security.jwt;

import lombok.Builder;

@Builder
public record JwtTokenPair(
        String accessToken,
        String refreshToken
) {
}
