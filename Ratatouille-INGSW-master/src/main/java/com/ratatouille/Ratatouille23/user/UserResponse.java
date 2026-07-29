package com.ratatouille.Ratatouille23.user;

import lombok.EqualsAndHashCode;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role
) {}
