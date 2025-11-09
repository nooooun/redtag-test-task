package org.example.backend.security.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.authentication.services.TokenBlacklistService;
import org.example.backend.exceptions.ErrorCode;
import org.example.backend.exceptions.ErrorResponse;
import org.example.backend.exceptions.authentication.TokenBlacklistedException;
import org.example.backend.security.endpoints.PublicEndpointConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenDecoder tokenDecoder;

    private final TokenBlacklistService blacklistService;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return PublicEndpointConfiguration.isPublic(request.getRequestURI(), request.getMethod());
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwtToken = request.getHeader("Authorization");
            jwtToken = jwtToken.substring(7);

            if (blacklistService.isBlacklisted(tokenDecoder.decodedJWT(jwtToken).getId())) {
                log.warn("JWT token blacklisted");
                throw new TokenBlacklistedException();
            }
            DecodedJWT decodedJWT = tokenDecoder.decodedJWT(jwtToken);
            Authentication authentication = tokenDecoder.getAuthentication(decodedJWT);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

        filterChain.doFilter(request, response);
    }
}
