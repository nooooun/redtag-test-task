package org.example.backend.authors.mappers;

import org.example.backend.authors.dto.request.CreateAuthorDTO;
import org.example.backend.authors.dto.response.AuthorResponseDTO;
import org.example.backend.authors.models.Author;

public interface AuthorMapper {
    Author fromCreateAuthorDTOToEntity(CreateAuthorDTO dto);
    AuthorResponseDTO fromEntityToResponseDTO(Author author);
}
