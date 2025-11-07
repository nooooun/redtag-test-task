package org.example.backend.exceptions.authentication;

public class TokenBlacklistedException extends RuntimeException {
    public TokenBlacklistedException() {
        super("Token blacklisted");
    }
}
