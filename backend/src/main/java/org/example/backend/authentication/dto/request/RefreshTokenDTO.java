package org.example.backend.authentication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenDTO(
        @NotNull @NotBlank String refreshToken
        ) {
}
