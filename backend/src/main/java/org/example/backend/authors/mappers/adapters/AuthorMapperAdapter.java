package org.example.backend.authors.mappers.adapters;

import org.example.backend.authors.dto.request.CreateAuthorDTO;
import org.example.backend.authors.dto.response.AuthorResponseDTO;
import org.example.backend.authors.mappers.AuthorMapper;
import org.example.backend.authors.models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapperAdapter extends AuthorMapper {

    @Override
    Author fromCreateAuthorDTOToEntity(CreateAuthorDTO dto);

    @Override
    AuthorResponseDTO fromEntityToResponseDTO(Author author);
}
