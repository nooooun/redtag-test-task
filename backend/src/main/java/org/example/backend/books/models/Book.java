package org.example.backend.books.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.example.backend.authors.models.Author;
import org.example.backend.books.enums.BookGenre;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @Column(name = "publish_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate publishDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private Author author;
}
