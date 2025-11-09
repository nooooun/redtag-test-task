package org.example.backend.exceptions.book;

import jakarta.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException() {
        super("Book not found");
    }
}
