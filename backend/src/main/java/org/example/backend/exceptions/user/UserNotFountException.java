package org.example.backend.exceptions.user;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFountException extends EntityNotFoundException {
    public UserNotFountException(String email) {
        super(String.format("User with email %s not found", email));
    }
}
