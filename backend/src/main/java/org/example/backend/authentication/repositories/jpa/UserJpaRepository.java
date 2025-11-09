package org.example.backend.authentication.repositories.jpa;

import org.example.backend.authentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByUuid(UUID uuid);
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
