package com.ratatouille.Ratatouille23.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ApiRequestException extends ResponseStatusException {
    public ApiRequestException(HttpStatusCode status) {
        super(status);
    }
    public ApiRequestException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
