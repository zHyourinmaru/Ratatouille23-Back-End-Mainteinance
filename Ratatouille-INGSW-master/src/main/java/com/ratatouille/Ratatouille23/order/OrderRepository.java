package com.ratatouille.Ratatouille23.order;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {


    default Optional<List<Order>> findOrders(Long id, LocalDateTime started, Long tableId, Long waiterId, Long cookId) {
        return Optional.of(findAll(OrderSpecification.findOrders(id, started, tableId, waiterId, cookId)));
    }

    default Optional<List<Order>> findOrdersByParams(Integer tableNumber, String waiterName, String cookName,
                                                     String waiterLastName, String cookLastName, LocalDateTime start,
                                                     LocalDateTime end, List<String> dishesNames) {
        return Optional.of(findAll(OrderSpecification.searchOrders(tableNumber, waiterName, cookName, waiterLastName,
                cookLastName, start, end, dishesNames)));
    }

    default Optional<Long> countOrders(Integer tableNumber, String waiterName, String cookName,
                                                     String waiterLastName, String cookLastName, LocalDateTime start,
                                                     LocalDateTime end, List<String> dishesNames) {
        return Optional.of((long) findAll(OrderSpecification.searchOrders(tableNumber, waiterName, cookName, waiterLastName,
                cookLastName, start, end, dishesNames)).size());
    }

    @Query(value = "SELECT o FROM Order o " +
            "WHERE o.waiter.id IN (SELECT u.id FROM User u WHERE u.role = 'WAITER' and u.id = :id) " +
            "OR o.cook.id IN (SELECT u.id FROM User u WHERE u.role = 'COOK' and u.id = :id)")
    Optional<List<Order>> findAllByUserId(Long id);
}
