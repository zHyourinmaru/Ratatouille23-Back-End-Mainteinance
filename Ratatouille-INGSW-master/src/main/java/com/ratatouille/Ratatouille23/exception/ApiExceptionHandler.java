package com.ratatouille.Ratatouille23.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException exception) {
        HttpStatusCode statusCode = exception.getStatusCode();
        ApiException payload = new ApiException(
                statusCode,
                statusCode.value(),
                exception.getReason(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(payload, statusCode);
    }
}
