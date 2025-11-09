package org.example.backend.authors.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record AuthorResponseDTO(
        Long id,
        String fullName,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate birthdate
) {
}
