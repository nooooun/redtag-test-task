package org.example.backend.authentication.dto.response;

import lombok.Builder;
import org.example.backend.security.jwt.JwtTokenPair;

@Builder
public record AuthResponseDTO(
        UserResponseDTO user,
        JwtTokenPair tokens
) {
}
