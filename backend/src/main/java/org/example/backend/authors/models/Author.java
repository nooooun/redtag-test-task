package org.example.backend.authors.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.example.backend.books.models.Book;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String fullName;

    @Column(name = "birthday", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;
}
