package org.example.backend.books.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.backend.books.enums.BookGenre;

import java.time.LocalDate;

public record CreateBookDTO(
        @NotNull @NotBlank String  title,
        @NotNull BookGenre genre,
        @NotNull @NotBlank String description,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        @NotNull LocalDate publishDate,
        @NotNull @NotBlank String authorFullName
) {
}
