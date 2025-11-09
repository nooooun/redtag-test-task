package org.example.backend.authentication.mappers;


import org.example.backend.authentication.dto.request.RegisterUserDTO;
import org.example.backend.authentication.dto.response.AuthResponseDTO;
import org.example.backend.authentication.models.User;
import org.example.backend.security.jwt.JwtTokenPair;

public interface UserMapper {
    User fromRegisterUserDTOToEntity(RegisterUserDTO dto);
    AuthResponseDTO fromEntityToAuthResponseDTO(User user, JwtTokenPair jwtTokenPair);
}
