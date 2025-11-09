package org.example.backend.authentication.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.authentication.dto.request.LoginDTO;
import org.example.backend.authentication.dto.request.RefreshTokenDTO;
import org.example.backend.authentication.dto.request.RegisterUserDTO;
import org.example.backend.authentication.dto.response.AuthResponseDTO;
import org.example.backend.authentication.services.AuthService;
import org.example.backend.security.jwt.JwtTokenPair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthResponseDTO> signup(@Valid @RequestBody RegisterUserDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(dto));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(dto));
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JwtTokenPair> refreshToken(@Valid @RequestBody RefreshTokenDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.refreshToken(dto));
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenDTO dto,
                                    @RequestHeader("Authorization") String accessToken) {
        authService.logout(accessToken, dto.refreshToken());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
