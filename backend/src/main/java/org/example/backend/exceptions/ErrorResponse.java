package org.example.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", shape = JsonFormat.Shape.STRING)
        LocalDateTime timestamp,
        Integer status,
        @Enumerated(EnumType.STRING)
        ErrorCode errorCode,
        String path,
        String className,
        String message,
        String trace
        ) {
}
