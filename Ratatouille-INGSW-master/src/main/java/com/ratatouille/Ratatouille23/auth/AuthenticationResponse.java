package com.ratatouille.Ratatouille23.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
){}
