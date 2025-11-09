package org.example.backend.books.services;

import org.example.backend.books.dto.request.BookFilterParamsDTO;
import org.example.backend.books.dto.request.CreateBookDTO;
import org.example.backend.books.dto.request.UpdateBookDetailsDTO;
import org.example.backend.books.dto.response.BookResponseDTO;
import org.example.backend.books.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDTO save(CreateBookDTO dto);
    Page<Book> findAll(BookFilterParamsDTO params, Pageable pageable);
    BookResponseDTO update(Long id, UpdateBookDetailsDTO dto);
    void delete(Long id);
    BookResponseDTO findById(Long id);
    BookResponseDTO addToFavorite(Long id);
    BookResponseDTO deleteFromFavorite(Long id);
}
