package com.ratatouille.Ratatouille23.order;

import com.ratatouille.Ratatouille23.allergen.Allergen;
import com.ratatouille.Ratatouille23.dish.Dish;
import com.ratatouille.Ratatouille23.dish.DishRepository;
import com.ratatouille.Ratatouille23.exception.ApiRequestException;
import com.ratatouille.Ratatouille23.table.TableRepository;
import com.ratatouille.Ratatouille23.user.Role;
import com.ratatouille.Ratatouille23.user.User;
import com.ratatouille.Ratatouille23.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    private final OrderRepository repository;
    private final DishRepository dishRepository;

    private final TableRepository tableRepository;
    private final UserRepository userRepository;

    private final OrderResponseMapper orderResponseMapper;

    public List<OrderResponse> getOrdersByAttributes(Long id, Integer tableNumber, String waiterName, String cookName,
                                                     String waiterLastName, String cookNameLastName, LocalDateTime start,
                                                     LocalDateTime end, List<String> dishesNames) {
            if (id != null) {
                return Collections.singletonList(repository.findById(id)
                        .map(orderResponseMapper)
                        .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "no orders with id " + id + " found!")));

            }
            return repository.findOrdersByParams(tableNumber, waiterName, cookName, waiterLastName, cookNameLastName, start, end, dishesNames)
                    .filter(list -> !list.isEmpty())
                    .map(list -> list
                            .stream()
                            .map(orderResponseMapper)
                            .collect(Collectors.toList()))
                    .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "no orders with this criteria found!"));
    }

    public List<OrderResponse> getOrdersByParameters(Long id, LocalDateTime timestamp, Long tableId, Long waiterId, Long cookId) {
        return repository.findOrders(
                id,
                timestamp,
                tableId,
                waiterId,
                cookId
                )
                .filter(list -> !list.isEmpty())
                .map(list -> list
                        .stream()
                        .map(orderResponseMapper)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "no orders with this criteria found!"));
    }

    public Long countOrders(Long id, Integer tableNumber, String waiterName, String cookName,
                            String waiterLastName, String cookNameLastName, LocalDateTime start,
                            LocalDateTime end, List<String> dishesNames) {
        if (id != null) {
            return Stream.of(getOrdersByParameters(id, null, null, null, null)).count();
        }
        return repository.countOrders(tableNumber, waiterName, cookName, waiterLastName, cookNameLastName, start, end, dishesNames)
                .orElseThrow(() ->  new ApiRequestException(HttpStatus.NOT_FOUND, "No orders with this criteria found!"));
    }

    public Long createOrder(OrderRequest request) {
        Order order = Order.builder()
                .started(LocalDateTime.now())
                .dishes(Optional.of(dishRepository.findAllById(request.dishes())).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Some dishes are not valid!")))
                .cook(request.cook() != null ? userRepository.findById(request.cook())
                        .filter(user -> user.getRole().equals(Role.COOK))
                        .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "This cook doesn't exists!")) : null)
                .waiter(userRepository.findById(request.waiter())
                        .filter(user -> user.getRole().equals(Role.WAITER))
                        .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "This waiter doesn't exists!")))
                .table(tableRepository.findById(request.table()).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "This table doesn't exists!")))
                .build();

        return repository.save(order).getId();
    }

    @Transactional
    public void updateOrder(Long id, OrderRequest request) {
        Order order = repository.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND,
                "Order with id " + id + " does not exists!"));

        if (order.getCompleted() != null) {
            throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Cannot modify a completed order!");
        }

        Long waiterId = request.waiter();
        Long cookId = request.cook();
        Long tableId = request.table();
        List<Dish> dishes = dishRepository.findAllById(request.dishes());
        LocalDateTime completed = request.completed();

        if (waiterId!= null && !Objects.equals(order.getWaiter().getId(), waiterId)) {
            order.setWaiter(userRepository.findById(waiterId)
                    .filter(user -> user.getRole().equals(Role.WAITER))
                    .orElseThrow(() -> new ApiRequestException(HttpStatus.BAD_REQUEST, "This user cannot be the order's waiter")));
        }
        if (cookId!= null && !Objects.equals(order.getCook().getId(), cookId)) {
            order.setCook(userRepository.findById(cookId)
                    .filter(user -> user.getRole().equals(Role.COOK))
                    .orElseThrow(() -> new ApiRequestException(HttpStatus.BAD_REQUEST, "This user cannot be the order's cook")));
        }
        if (tableId!= null && !Objects.equals(order.getTable().getId(), tableId)) {
            order.setTable(tableRepository.findById(tableId)
                    .orElseThrow(() -> new ApiRequestException(HttpStatus.BAD_REQUEST, "This user cannot be the order's cook")));
        }
        if (!dishes.isEmpty() && !Objects.equals(order.getDishes(), dishes)) {
            order.setDishes(dishes);
        }
        if (completed != null && completed.isAfter(order.getStarted()) && order.getCompleted() != null) {
            order.setCompleted(completed);
        }
    }

    public void deleteOrder(Long id) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Order with id " + id + " does not exists!");
        }
        repository.deleteById(id);
    }

    @Transactional
    public Long completeOrder(Long id, Long cookId) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Order with id " + id + " does not exists!"));
        if (cookId != null) {
            User user = userRepository.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User with id " + id + " does not exists!"));
            if (user.getRole().equals(Role.COOK)) {
                order.setCook(user);
            } else {
                throw new ApiRequestException(HttpStatus.BAD_REQUEST, "This user is not a cook!");
            }
        }
        order.setCompleted(LocalDateTime.now());
        return order.getId();
    }
}
