package com.ratatouille.Ratatouille23.auth;

import lombok.Builder;

@Builder
public record AuthenticationRequest(
        String email,
        String password
){}
