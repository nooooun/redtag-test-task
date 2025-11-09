package org.example.backend.authentication.services.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.authentication.dto.request.LoginDTO;
import org.example.backend.authentication.dto.request.RefreshTokenDTO;
import org.example.backend.authentication.dto.request.RegisterUserDTO;
import org.example.backend.authentication.dto.response.AuthResponseDTO;
import org.example.backend.authentication.mappers.UserMapper;
import org.example.backend.authentication.models.RefreshToken;
import org.example.backend.authentication.models.User;
import org.example.backend.authentication.repositories.RefreshTokenRepository;
import org.example.backend.authentication.repositories.UserRepository;
import org.example.backend.authentication.services.TokenBlacklistService;
import org.example.backend.authentication.services.AuthService;
import org.example.backend.exceptions.authentication.TokenBlacklistedException;
import org.example.backend.exceptions.user.UserExistsException;
import org.example.backend.exceptions.user.UserNotFountException;
import org.example.backend.exceptions.authentication.WrongPasswordException;
import org.example.backend.security.jwt.JwtTokenDecoder;
import org.example.backend.security.jwt.JwtTokenGenerator;
import org.example.backend.security.jwt.JwtTokenPair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenGenerator tokenGenerator;

    private final JwtTokenDecoder tokenDecoder;

    private final TokenBlacklistService tokenBlacklistService;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public AuthResponseDTO signup(RegisterUserDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserExistsException(dto.email());
        }

        var user = userMapper.fromRegisterUserDTOToEntity(dto);
        user.setPasswordHash(passwordEncoder.encode(dto.password()));

        user = userRepository.save(user);

        var jwtTokens = getJwtTokenPair(user);

        DecodedJWT decodedRefreshToken = tokenDecoder.decodedJWT(jwtTokens.refreshToken());
        refreshTokenRepository.save(
                buildRefreshTokenRecord(decodedRefreshToken, user)
        );

        return userMapper.fromEntityToAuthResponseDTO(user, jwtTokens);
    }

    @Override
    public AuthResponseDTO login(LoginDTO dto) {
        var user = userRepository.findByEmail(dto.email());

        if (user == null) {
            throw new UserNotFountException(dto.email());
        }

        if (!passwordEncoder.matches(dto.password(), user.getPasswordHash())) {
            throw new WrongPasswordException();
        }

        JwtTokenPair jwtTokenPair = getJwtTokenPair(user);

        DecodedJWT decodedRefreshToken = tokenDecoder.decodedJWT(jwtTokenPair.refreshToken());
        refreshTokenRepository.save(
            buildRefreshTokenRecord(decodedRefreshToken, user)
        );

        return userMapper.fromEntityToAuthResponseDTO(user, jwtTokenPair);
    }

    @Override
    public JwtTokenPair refreshToken(RefreshTokenDTO dto) {
        DecodedJWT refreshToken = tokenDecoder.decodedJWT(dto.refreshToken());

        if (!refreshTokenRepository.existsByJti(refreshToken.getId())) {
            throw new TokenBlacklistedException();
        }

        refreshTokenRepository.deleteByJti(refreshToken.getId());

        return tokenGenerator.generateAccessAndRefreshTokens(
                SecurityContextHolder.getContext().getAuthentication()
        );
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        DecodedJWT decodedAccessToken = tokenDecoder.decodedJWT(accessToken.substring(7));
        DecodedJWT decodedRefreshToken = tokenDecoder.decodedJWT(refreshToken);
        tokenBlacklistService.blacklistToken(decodedAccessToken.getId(),
                decodedAccessToken.getExpiresAtAsInstant());
        refreshTokenRepository.deleteByJti(decodedRefreshToken.getId());
    }

    private JwtTokenPair getJwtTokenPair(User user) {
        var authentication = new UsernamePasswordAuthenticationToken(
                user.getUuid(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString()))
        );

        return tokenGenerator.generateAccessAndRefreshTokens(authentication);
    }

    private RefreshToken buildRefreshTokenRecord(DecodedJWT decodedRefreshToken, User user) {
        return RefreshToken.builder()
                .jti(decodedRefreshToken.getId())
                .expireTime(decodedRefreshToken.getExpiresAtAsInstant())
                .user(user)
                .build();
    }
}
