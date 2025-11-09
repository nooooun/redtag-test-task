package org.example.backend.books.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.backend.books.enums.BookGenre;

import java.time.LocalDate;
import java.util.List;

public record BookFilterParamsDTO(
        String title,
        List<BookGenre> bookGenres,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate publishedBefore,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate publishedAfter,
        String authorFullName
) {
}
