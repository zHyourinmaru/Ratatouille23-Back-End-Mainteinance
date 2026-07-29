package com.ratatouille.Ratatouille23.exception;

import org.springframework.http.HttpStatusCode;

import java.time.ZonedDateTime;

public record ApiException(
        HttpStatusCode statusCode,
        Integer statusCodeValue,
        String message,
        ZonedDateTime time
)
{}
