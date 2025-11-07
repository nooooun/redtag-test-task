package org.example.backend.authentication.repositories;

import org.example.backend.authentication.dto.request.RegisterUserDTO;
import org.example.backend.authentication.models.User;

import java.util.UUID;

public interface UserRepository {
    User save(User user);
    User findByUuid(UUID uuid);
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
