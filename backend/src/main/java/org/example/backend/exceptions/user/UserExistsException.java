package org.example.backend.exceptions.user;

import jakarta.persistence.EntityExistsException;

public class UserExistsException extends EntityExistsException {
    public UserExistsException(String email) {
        super(String.format("User with email: %s already exists", email));
    }
}
