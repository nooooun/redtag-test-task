package org.example.backend.books.repositories;

import org.example.backend.books.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BookRepository {
    Book save(Book book);
    Page<Book> findAll(Specification<Book> specification, Pageable pageable);
    void delete(String title);
}
