package org.example.backend.books.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.authors.models.Author;
import org.example.backend.authors.services.AuthorService;
import org.example.backend.books.dto.request.BookFilterParamsDTO;
import org.example.backend.books.dto.request.CreateBookDTO;
import org.example.backend.books.dto.response.BookResponseDTO;
import org.example.backend.books.mappers.BookMapper;
import org.example.backend.books.models.Book;
import org.example.backend.books.repositories.BookRepository;
import org.example.backend.books.services.BookService;
import org.example.backend.utils.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final AuthorService authorService;

    public BookResponseDTO save(CreateBookDTO dto) {

    }

    private Specification<Book> buildSpecification(BookFilterParamsDTO params) {
        return ((Specification<Book>) (root, query, cb) -> null)
                .and(SpecificationUtils.iLike(Book.Fields.title, params.title()))
                .and(SpecificationUtils.in(Book.Fields.genre, params.bookGenres()))
                .and(SpecificationUtils.gte(Book.Fields.publishDate, params.publishedAfter()))
                .and(SpecificationUtils.lte(Book.Fields.publishDate, params.publishedBefore()))
                .and(SpecificationUtils.joinLike(Book.Fields.author, Author.Fields.fullName, params.authorFullName()));
    }
}
