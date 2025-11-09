package org.example.backend.security.endpoints;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Component
public class PublicEndpointConfiguration {

    private final static AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static final List<PublicEndpoint> publicEndpoint = List.of(
            new PublicEndpoint("/api/user/signup", HttpMethod.POST),
            new PublicEndpoint("/api/user/login", HttpMethod.POST)
    );

    public static Boolean isPublic(String path, String method) {
        return publicEndpoint.stream()
                .anyMatch(endpoint -> antPathMatcher.match(endpoint.pattern(), path) &&
                        endpoint.method().matches(method));
    }
}
