package com.ratatouille.Ratatouille23.order;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class OrderSpecification {
    public static Specification<Order> findOrders(Long id, LocalDateTime started, Long tableId, Long waiterId, Long cookId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            if (started != null) {
                predicates.add(cb.equal(root.get("started"), started));
            }
            if (tableId != null) {
                predicates.add(cb.equal(root.get("table").get("id"), tableId));
            }
            if (waiterId != null) {
                predicates.add(cb.equal(root.get("waiter").get("id"), waiterId));
            }
            if (cookId != null) {
                predicates.add(cb.equal(root.get("cook").get("id"), cookId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Order> searchOrders(Integer tableNumber, String waiterName, String cookName,
                                                    String waiterLastName, String cookLastName, LocalDateTime start,
                                                    LocalDateTime end, List<String> dishesNames) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (start != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("started"), start));
            }
            if (end != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("completed"), end));
            }
            if (tableNumber != null) {
                predicates.add(cb.like(root.get("table").get("number").as(String.class), "%" + tableNumber + "%"));
            }
            if (waiterName != null) {
                predicates.add(cb.like(cb.lower(root.get("waiter").get("firstName")), "%" + waiterName.toLowerCase() + "%"));
            }
            if (cookName != null) {
                predicates.add(cb.like(cb.lower(root.get("cook").get("firstName")), "%" + cookName + "%"));
            }
            if (waiterLastName != null) {
                predicates.add(cb.like(cb.lower(root.get("waiter").get("lastName")), "%" + waiterLastName + "%"));
            }
            if (cookLastName != null) {
                predicates.add(cb.like(cb.lower(root.get("cook").get("lastName")), "%" + cookLastName + "%"));
            }

            if (dishesNames != null && !dishesNames.isEmpty()) {
                List<Predicate> contains = new ArrayList<>();
                dishesNames.forEach(dishName -> {
                    contains.add(cb.like(cb.lower(root.join("dishes").get("name")), "%" + dishName.toLowerCase() + "%"));
                });
                predicates.add(cb.or(contains.toArray(new Predicate[contains.size()])));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}