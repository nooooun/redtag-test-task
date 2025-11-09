package org.example.backend.exceptions.author;

import jakarta.persistence.EntityNotFoundException;

public class AuthorNotFoundException extends EntityNotFoundException {
    public AuthorNotFoundException() {
        super("Author not found");
    }
}
