package org.example.backend.exceptions.authentication;

import org.springframework.security.authentication.BadCredentialsException;

public class WrongPasswordException extends BadCredentialsException {
    public WrongPasswordException() {
        super("Wrong password");
    }
}
