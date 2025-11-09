package org.example.backend.exceptions.authentication;

public class RefreshTokenFailedException extends RuntimeException {
    public RefreshTokenFailedException(String message) {
        super(message);
    }
}
