package com.ratatouille.Ratatouille23.user;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserSmallResponseMapper implements Function<User, UserSmallResponse> {
    @Override
    public UserSmallResponse apply(User user) {
        return  new UserSmallResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
