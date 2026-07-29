package com.ratatouille.Ratatouille23.allergen;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

class AllergenSpecification {

    public static Specification<Allergen> searchAllergens(String name, List<String> dishesNames) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name + "%"));
            }

            if (dishesNames != null && !dishesNames.isEmpty()) {
                List<Predicate> contains = new ArrayList<>();
                dishesNames.forEach(dishName -> {
                    contains.add(cb.like(cb.lower(root.join("dishes").get("name")), "%" + dishName.toLowerCase() + "%"));
                });
                predicates.add(cb.or(contains.toArray(new Predicate[contains.size()])));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
