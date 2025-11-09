package org.example.backend.authors.services;

import org.example.backend.authors.dto.request.CreateAuthorDTO;
import org.example.backend.authors.dto.response.AuthorResponseDTO;
import org.example.backend.authors.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    AuthorResponseDTO addAuthor(CreateAuthorDTO dto);
    Page<Author> findAll(String fullName, Pageable pageable);
    AuthorResponseDTO findById(Long id);
    void delete(Long id);
}
