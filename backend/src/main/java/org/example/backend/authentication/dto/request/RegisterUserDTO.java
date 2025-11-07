package org.example.backend.authentication.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.backend.authentication.enums.Role;

public record RegisterUserDTO(
        @NotNull String username,
        @NotNull String email,
        @NotNull String password,
        @NotNull Role role
        ) {
}
