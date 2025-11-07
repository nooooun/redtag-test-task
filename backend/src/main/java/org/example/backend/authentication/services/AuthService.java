package org.example.backend.authentication.services;

import org.example.backend.authentication.dto.request.LoginDTO;
import org.example.backend.authentication.dto.request.RefreshTokenDTO;
import org.example.backend.authentication.dto.request.RegisterUserDTO;
import org.example.backend.authentication.dto.response.AuthResponseDTO;
import org.example.backend.security.jwt.JwtTokenPair;

public interface AuthService {
    AuthResponseDTO signup(RegisterUserDTO dto);
    AuthResponseDTO login(LoginDTO dto);
    JwtTokenPair refreshToken(RefreshTokenDTO dto);
    void logout(String accessToken, String refreshToken);
}
