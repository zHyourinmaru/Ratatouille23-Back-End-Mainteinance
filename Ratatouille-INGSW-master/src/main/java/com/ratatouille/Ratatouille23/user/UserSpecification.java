package com.ratatouille.Ratatouille23.user;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

class UserSpecification {

    public static Specification<User> searchUsers(String firstName, String lastName, String email, Role role) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (firstName != null) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
            }
            if (lastName != null) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }
            if (email != null) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }
            if (role != null) {
                predicates.add(cb.equal(root.get("role"), role));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
