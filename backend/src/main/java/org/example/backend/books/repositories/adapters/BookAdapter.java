package org.example.backend.books.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.example.backend.books.models.Book;
import org.example.backend.books.repositories.BookRepository;
import org.example.backend.books.repositories.jpa.BookJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookAdapter implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public Book save(Book book) {
        return bookJpaRepository.save(book);
    }

    @Override
    public Page<Book> findAll(Specification<Book> specification, Pageable pageable) {
        return bookJpaRepository.findAll(specification, pageable);
    }

    @Override
    public void delete(String title) {
        bookJpaRepository.deleteBookByTitle(title);
    }
}
