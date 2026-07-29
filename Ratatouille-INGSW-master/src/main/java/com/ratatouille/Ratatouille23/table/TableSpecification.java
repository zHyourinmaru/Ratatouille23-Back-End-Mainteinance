package com.ratatouille.Ratatouille23.table;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

class TableSpecification {

    public static Specification<Table> searchTables(Integer number, Integer capacity) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (number != null) {
                predicates.add(cb.like(root.get("number").as(String.class), "%" + number + "%"));
            }
            if (capacity != null) {
                predicates.add(cb.like(root.get("capacity").as(String.class), "%" + capacity + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
