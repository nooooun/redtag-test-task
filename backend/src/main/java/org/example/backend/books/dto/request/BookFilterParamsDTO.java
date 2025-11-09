package org.example.backend.books.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.example.backend.books.enums.BookGenre;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookFilterParamsDTO  {
        private String title;
        private List<BookGenre> bookGenres;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate publishedBefore;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate publishedAfter;
        private String authorFullName;
        private UUID userUuid;
}
