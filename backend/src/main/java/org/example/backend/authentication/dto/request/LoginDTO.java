package org.example.backend.authentication.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull String email,
        @NotNull String password
) {
}
