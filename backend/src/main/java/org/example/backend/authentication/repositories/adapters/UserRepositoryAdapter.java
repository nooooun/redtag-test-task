package org.example.backend.authentication.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.example.backend.authentication.models.User;
import org.example.backend.authentication.repositories.jpa.UserJpaRepository;
import org.example.backend.authentication.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository dataRepository;

    @Override
    public User save(User user) {
        return dataRepository.save(user);
    }

    @Override
    public User findByUuid(UUID uuid) {
        return dataRepository.findByUuid(uuid);
    }

    @Override
    public User findByEmail(String email) {
        return dataRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return dataRepository.existsByEmail(email);
    }
}
