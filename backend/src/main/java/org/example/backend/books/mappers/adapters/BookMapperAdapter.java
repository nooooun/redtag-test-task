package org.example.backend.books.mappers.adapters;

import org.example.backend.books.dto.request.CreateBookDTO;
import org.example.backend.books.dto.request.UpdateBookDetailsDTO;
import org.example.backend.books.dto.response.BookResponseDTO;
import org.example.backend.books.mappers.BookMapper;
import org.example.backend.books.models.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapperAdapter extends BookMapper {

    @Override
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", ignore = true)
    Book fromDTOToEntity(CreateBookDTO dto);

    @Override
    @Mapping(source = "author.fullName", target = "authorFullName")
    BookResponseDTO fromEntityToResponseDTO(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    void updateBookFromDTO(UpdateBookDetailsDTO dto, @MappingTarget Book book);
}
