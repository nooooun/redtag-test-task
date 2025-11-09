package org.example.backend.books.mappers;

import org.example.backend.books.dto.request.CreateBookDTO;
import org.example.backend.books.dto.response.BookResponseDTO;
import org.example.backend.books.models.Book;

public interface BookMapper {

    Book fromDTOToEntity(CreateBookDTO dto);

    BookResponseDTO fromEntityToResponseDTO(Book book);
}
