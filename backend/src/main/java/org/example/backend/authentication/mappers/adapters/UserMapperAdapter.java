package org.example.backend.authentication.mappers.adapters;

import org.example.backend.authentication.dto.request.RegisterUserDTO;
import org.example.backend.authentication.dto.response.AuthResponseDTO;
import org.example.backend.authentication.mappers.UserMapper;
import org.example.backend.authentication.models.User;
import org.example.backend.security.jwt.JwtTokenPair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapperAdapter extends UserMapper {

    @Override
    @Mapping(target = "passwordHash", ignore = true)
    User fromRegisterUserDTOToEntity(RegisterUserDTO dto);

    @Override
    @Mapping(source = "user", target = "user")
    @Mapping(source = "jwtTokenPair", target = "tokens")
    AuthResponseDTO fromEntityToAuthResponseDTO(User user, JwtTokenPair jwtTokenPair);
}
