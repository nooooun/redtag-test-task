package org.example.backend.authentication.services;

import java.time.Instant;

public interface TokenBlacklistService {
    void blacklistToken(String jti, Instant expireTime);
    Boolean isBlacklisted(String jti);
}
