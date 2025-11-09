package org.example.backend.books.repositories.jpa;

import org.example.backend.books.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Book findByTitle(String title);
    Integer deleteBookByTitle(String title);
}
