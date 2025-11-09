package org.example.backend.authentication.dto.response;

import org.example.backend.authentication.enums.Role;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String username,
        String email,
        Role role
//        LocalDateTime createdAt,
//        LocalDateTime modifiedAt
) {
}
