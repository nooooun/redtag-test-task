package org.example.backend.authors.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.authors.dto.request.CreateAuthorDTO;
import org.example.backend.authors.dto.response.AuthorResponseDTO;
import org.example.backend.authors.mappers.AuthorMapper;
import org.example.backend.authors.models.Author;
import org.example.backend.authors.repositories.AuthorRepository;
import org.example.backend.authors.services.AuthorService;
import org.example.backend.utils.SpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    public AuthorResponseDTO addAuthor(CreateAuthorDTO dto) {
        var author = authorMapper.fromCreateAuthorDTOToEntity(dto);
        author = authorRepository.save(author);
        return authorMapper.fromEntityToResponseDTO(author);
    }

    @Override
    public Page<Author> findAll(String fullName, Pageable pageable) {
        return authorRepository.findAll(
                buildSpecification(fullName),
                pageable
        );
    }

    @Override
    public AuthorResponseDTO findById(Long id) {
        return authorMapper
                .fromEntityToResponseDTO(
                        authorRepository.findById(id)
                );
    }

    @Override
    public void delete(Long id) {
        authorRepository.delete(id);
    }

    private Specification<Author> buildSpecification(String fullName) {
        return ((Specification<Author>)(root, query, cb) -> null)
                .and(SpecificationUtils.iLike(Author.Fields.fullName, fullName));
    }
}
