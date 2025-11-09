package org.example.backend.exceptions.author;

import jakarta.persistence.EntityNotFoundException;

public class AuthorNotFoundException extends EntityNotFoundException {
    public AuthorNotFoundException(Long id) {
        super(String.format("Author with id: %d not found", id));
    }
}
