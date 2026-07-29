package com.ratatouille.Ratatouille23.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public record UserRequest (
        String firstName,
        String lastName,
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE)
        String email,
        String password,
        Role role
)
{}
