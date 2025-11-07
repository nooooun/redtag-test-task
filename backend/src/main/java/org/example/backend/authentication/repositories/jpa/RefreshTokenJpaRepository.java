package org.example.backend.authentication.repositories.jpa;

import org.example.backend.authentication.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByJti(String Jti);
    void deleteByJti(String Jti);
}
