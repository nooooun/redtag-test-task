package org.example.backend.authors.repositories.jpa;

import org.example.backend.authors.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorJpaRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    Author findByFullName(String fullName);
}
