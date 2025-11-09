package org.example.backend.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncodingService {

    private static BCryptPasswordEncoder passwordEncoder;

    public static String encodePassword(String password) {
        if (password == null || password.isBlank()) {
            return null;
        }

        return passwordEncoder.encode(password);
    }
}
