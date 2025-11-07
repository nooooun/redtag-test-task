package org.example.backend.security.endpoints;

import org.springframework.http.HttpMethod;

public record PublicEndpoint(
        String pattern,
        HttpMethod method
) {
}
