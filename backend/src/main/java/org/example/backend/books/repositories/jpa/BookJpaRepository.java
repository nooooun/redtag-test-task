package org.example.backend.books.repositories.jpa;

import org.example.backend.books.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Book findByTitle(String title);
    Integer deleteBookById(Long id);
    @Query("""
        SELECT DISTINCT b
        FROM Book b
        INNER JOIN User u
        WHERE u.uuid =: userUuid
        """)
    Book findUsersFavorite(Specification<Book> specification, UUID userUuid);
}
