package com.ratatouille.Ratatouille23.dish;

import com.ratatouille.Ratatouille23.order.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class DishSpecification {
    public static Specification<Dish> searchDishes(String name, Integer price, List<String> allergenNames) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name + "%"));
            }
            if (price != null) {
                predicates.add(cb.like( root.get("price").as(String.class),"%" + price+ "%"));
            }
            if (allergenNames != null && !allergenNames.isEmpty()) {
                List<Predicate> contains = new ArrayList<>();
                allergenNames.forEach(allergenName -> {
                    contains.add(cb.isTrue(
                            cb.lower(root.join("allergens").get("name")).in(allergenNames)));
                });
                predicates.add(cb.or(contains.toArray(new Predicate[predicates.size()])));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
