package org.example.backend.authors.repositories;

import org.example.backend.authors.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AuthorRepository {
    Author save(Author author);
    Page<Author> findAll(Specification<Author> specification, Pageable pageable);
    void delete(Long id);
    Author findById(Long id);
    Author findByFullName(String fullName);
}
