package org.example.backend.books.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.backend.books.enums.BookGenre;

import java.time.LocalDate;

public record UpdateBookDetailsDTO(
        @NotBlank String title,
        BookGenre genre,
        @NotBlank String description,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate publishDate
) {
}
