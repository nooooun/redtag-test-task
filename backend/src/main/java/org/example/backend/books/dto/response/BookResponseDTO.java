package org.example.backend.books.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.backend.books.enums.BookGenre;

import java.time.LocalDate;

public record BookResponseDTO(
        Long id,
        String title,
        BookGenre genre,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate publishDate,
        String authorFullName
) {
}
