package org.example.backend.exceptions;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.exceptions.authentication.RefreshTokenFailedException;
import org.example.backend.exceptions.authentication.TokenBlacklistedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> defaultExceptionHandler(Exception e, HttpServletRequest request) {
        var errorResponse = buildErrorResponse(e, ErrorCode.DEFAULT,
                request, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> entityNotFoundExceptionHandler(Exception e, HttpServletRequest request) {
        var errorResponse = buildErrorResponse(e, ErrorCode.RESOURCE_NOT_FOUND,
                request, HttpServletResponse.SC_NOT_FOUND);

        return ResponseEntity
                .status(HttpServletResponse.SC_NOT_FOUND)
                .header("Content-Type", "application/json")
                .body(errorResponse);
    }

    @ExceptionHandler({TokenExpiredException.class, JWTDecodeException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> authenticationExceptionHandler(Exception e, HttpServletRequest request) {
        var errorCode = e instanceof TokenExpiredException ? ErrorCode.ASSESS_TOKEN_EXPIRED : ErrorCode.DEFAULT;

        var errorResponse = buildErrorResponse(e, errorCode,
                request, HttpServletResponse.SC_UNAUTHORIZED);

        return ResponseEntity
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(errorResponse);
    }

    @ExceptionHandler(RefreshTokenFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> refreshTokenFailedExceptionHandler(RefreshTokenFailedException e, HttpServletRequest request) {
        var errorResponse = buildErrorResponse(e, ErrorCode.REFRESH_TOKEN_FAILED,
                request, HttpServletResponse.SC_UNAUTHORIZED);

        return ResponseEntity
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(errorResponse);
    }

    @ExceptionHandler(TokenBlacklistedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> tokenBlacklistedException(TokenBlacklistedException e, HttpServletRequest request) {
        var errorResponse = buildErrorResponse(e, ErrorCode.TOKEN_BLACKLISTED,
                request, HttpServletResponse.SC_UNAUTHORIZED);

        return ResponseEntity
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(errorResponse);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> entityExistsException(EntityExistsException e, HttpServletRequest request) {
        var errorResponse = buildErrorResponse(e, ErrorCode.RESOURCE_ALREADY_EXISTS,
                request, HttpServletResponse.SC_BAD_REQUEST);

        return ResponseEntity
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(errorResponse);
    }

    private ErrorResponse buildErrorResponse(Exception e, ErrorCode errorCode, HttpServletRequest request, Integer status) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .errorCode(errorCode)
                .path(request.getRequestURI())
                .className(e.getClass().toString())
                .message(e.getMessage())
                .trace(Arrays.toString(e.getStackTrace()))
                .build();
    }
}
