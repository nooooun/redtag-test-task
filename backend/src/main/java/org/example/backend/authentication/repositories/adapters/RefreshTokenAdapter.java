package org.example.backend.authentication.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.example.backend.authentication.models.RefreshToken;
import org.example.backend.authentication.repositories.RefreshTokenRepository;
import org.example.backend.authentication.repositories.jpa.RefreshTokenJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenAdapter implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(refreshToken);
    }

    @Override
    public Boolean existsByJti(String jti) {
        return refreshTokenJpaRepository.existsByJti(jti);
    }

    @Override
    @Transactional
    public void deleteByJti(String jti) {
        refreshTokenJpaRepository.deleteByJti(jti);
    }
}
