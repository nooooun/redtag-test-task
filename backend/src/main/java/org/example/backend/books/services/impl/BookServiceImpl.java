package org.example.backend.books.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.authentication.models.User;
import org.example.backend.authentication.repositories.UserRepository;
import org.example.backend.authors.models.Author;
import org.example.backend.authors.repositories.AuthorRepository;
import org.example.backend.books.dto.request.BookFilterParamsDTO;
import org.example.backend.books.dto.request.CreateBookDTO;
import org.example.backend.books.dto.request.UpdateBookDetailsDTO;
import org.example.backend.books.dto.response.BookResponseDTO;
import org.example.backend.books.mappers.BookMapper;
import org.example.backend.books.models.Book;
import org.example.backend.books.repositories.BookRepository;
import org.example.backend.books.services.BookService;
import org.example.backend.exceptions.author.AuthorNotFoundException;
import org.example.backend.security.services.SecurityPrincipalService;
import org.example.backend.utils.SpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final AuthorRepository authorRepository;

    private final UserRepository userRepository;

    @Override
    public BookResponseDTO save(CreateBookDTO dto) {
        var author = authorRepository.findByFullName(dto.authorFullName());

        if (author == null) {
            throw new AuthorNotFoundException();
        }

        var book = bookMapper.fromDTOToEntity(dto);
        book.setAuthor(author);

        book = bookRepository.save(book);

        return bookMapper.fromEntityToResponseDTO(book);
    }

    @Override
    public Page<Book> findAll(BookFilterParamsDTO params, Pageable pageable) {
        return bookRepository.findAll(
                buildSpecification(params),
                pageable
        );
    }

    @Override
    @Transactional
    public BookResponseDTO update(Long id, UpdateBookDetailsDTO dto) {
        var book = bookRepository.findById(id);

        bookMapper.updateBookFromDTO(dto, book);

        return bookMapper.fromEntityToResponseDTO(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public BookResponseDTO findById(Long id) {
        return bookMapper.fromEntityToResponseDTO(bookRepository.findById(id));
    }

    @Override
    @Transactional
    public BookResponseDTO addToFavorite(Long id) {
        var book = bookRepository.findById(id);
        book.getUsersThatAddedToFavorite().add(userRepository.findByUuid(SecurityPrincipalService.getPrincipalUUID()));
        return bookMapper.fromEntityToResponseDTO(book);
    }

    @Override
    @Transactional
    public BookResponseDTO deleteFromFavorite(Long id) {
        var book = bookRepository.findById(id);
        book.getUsersThatAddedToFavorite().remove(userRepository.findByUuid(SecurityPrincipalService.getPrincipalUUID()));
        return bookMapper.fromEntityToResponseDTO(book);
    }

    private Specification<Book> buildSpecification(BookFilterParamsDTO params) {
        if (params == null) {
            return null;
        }
        return ((Specification<Book>) (root, query, cb) -> null)
                .and(SpecificationUtils.iLike(Book.Fields.title, params.getTitle()))
                .and(SpecificationUtils.in(Book.Fields.genre, params.getBookGenres()))
                .and(SpecificationUtils.gte(Book.Fields.publishDate, params.getPublishedAfter()))
                .and(SpecificationUtils.lte(Book.Fields.publishDate, params.getPublishedBefore()))
                .and(SpecificationUtils.joinLike(Book.Fields.author,
                        Author.Fields.fullName, params.getAuthorFullName()))
                .and(SpecificationUtils.joinEqual(Book.Fields.usersThatAddedToFavorite,
                        User.Fields.uuid, params.getUserUuid()));
    }
}
