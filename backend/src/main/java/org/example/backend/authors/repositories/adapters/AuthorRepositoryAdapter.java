package org.example.backend.authors.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.example.backend.authors.models.Author;
import org.example.backend.authors.repositories.AuthorRepository;
import org.example.backend.authors.repositories.jpa.AuthorJpaRepository;
import org.example.backend.exceptions.author.AuthorNotFoundException;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorRepositoryAdapter implements AuthorRepository {

    private final AuthorJpaRepository authorJpaRepository;

    @Override
    public Author save(Author author) {
        return authorJpaRepository.save(author);
    }

    @Override
    public Page<Author> findAll(Specification<Author> specification, Pageable pageable) {
        return authorJpaRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        authorJpaRepository.deleteById(id);
    }

    @Override
    public Author findById(Long id) {
        return authorJpaRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
