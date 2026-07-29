package com.ratatouille.Ratatouille23.auth;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
){}
