package org.example.backend.books.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.example.backend.authentication.models.User;
import org.example.backend.authors.models.Author;
import org.example.backend.books.enums.BookGenre;

import java.time.LocalDate;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_books",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_FAVORITE_BOOKS_ON_BOOKS",
                            value = ConstraintMode.CONSTRAINT)
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_uuid",
                    referencedColumnName = "uuid",
                    foreignKey = @ForeignKey(name = "FK_FAVORITE_BOOKS_ON_USERS",
                            value = ConstraintMode.CONSTRAINT)
            )
    )
    private Set<User> usersThatAddedToFavorite;
}
