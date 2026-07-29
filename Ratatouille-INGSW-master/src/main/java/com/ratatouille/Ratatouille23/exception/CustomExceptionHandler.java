package com.ratatouille.Ratatouille23.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final ObjectMapper objectMapper;
    @ExceptionHandler
    public ResponseEntity<ApiException> handleAccessDeniedException(RuntimeException exception, HttpServletRequest request) throws JsonProcessingException {
        ApiException payload = new ApiException(
                HttpStatus.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(payload);
    }

}