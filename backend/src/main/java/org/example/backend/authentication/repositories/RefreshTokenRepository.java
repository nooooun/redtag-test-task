package org.example.backend.authentication.repositories;

import org.example.backend.authentication.models.RefreshToken;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Boolean existsByJti(String Jti);
    void deleteByJti(String Jti);
}
