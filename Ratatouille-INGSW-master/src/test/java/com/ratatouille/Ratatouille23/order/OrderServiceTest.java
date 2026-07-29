package com.ratatouille.Ratatouille23.order;

import com.ratatouille.Ratatouille23.dish.Dish;
import com.ratatouille.Ratatouille23.dish.DishRepository;
import com.ratatouille.Ratatouille23.dish.DishSmallResponseMapper;
import com.ratatouille.Ratatouille23.exception.ApiRequestException;
import com.ratatouille.Ratatouille23.table.Table;
import com.ratatouille.Ratatouille23.table.TableRepository;
import com.ratatouille.Ratatouille23.table.TableResponseMapper;
import com.ratatouille.Ratatouille23.user.Role;
import com.ratatouille.Ratatouille23.user.User;
import com.ratatouille.Ratatouille23.user.UserRepository;
import com.ratatouille.Ratatouille23.user.UserSmallResponseMapper;
import jakarta.transaction.Transactional;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class OrderServiceTest {
    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final DishRepository dishRepository = mock(DishRepository.class);
    private final TableRepository tableRepository = mock(TableRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final OrderResponseMapper orderResponseMapper = mock(OrderResponseMapper.class);
    private final OrderService orderService = new OrderService(orderRepository, dishRepository, tableRepository, userRepository, orderResponseMapper);


    @Test
    @Transactional
    @Rollback
    void testUpdateOrder() {
        // Preparare dati di test nel database
        User waiter = new User("Walter", "Beat","waiter@example.com", "securepassword", Role.WAITER);
        User cook = new User("Dean","Arrow", "cook@example.com", "faker123", Role.COOK);
        Table table = new Table(1L, 12, 1);
        Dish dish1 = new Dish(1L,"Pizza", "Delicious pizza", 10, List.of());
        Dish dish2 = new Dish(2L, "Pasta", "Tasty pasta", 8, List.of());

        userRepository.save(waiter);
        userRepository.save(cook);
        tableRepository.save(table);
        dishRepository.save(dish1);
        dishRepository.save(dish2);

        Order order = new Order(1L, LocalDateTime.now(), null, waiter, cook, table, List.of(dish1));
        orderRepository.save(order);

        Long orderId = order.getId();
        OrderRequest request = new OrderRequest(List.of(dish2.getId()), table.getId(), waiter.getId(), table.getId(), LocalDateTime.now());

        orderService.updateOrder(orderId, request);

        Order updatedOrder = orderRepository.findById(orderId).orElseThrow();
        assertEquals(cook.getId(), updatedOrder.getCook().getId());
        assertEquals(waiter.getId(), updatedOrder.getWaiter().getId());
        assertEquals(table.getId(), updatedOrder.getTable().getId());
        assertEquals(List.of(dish1, dish2), updatedOrder.getDishes());
        assertEquals(request.completed(), updatedOrder.getCompleted());
    }

    @Test
    @Transactional
    void testUpdateCompletedOrder() {
        // Preparare dati di test nel database
        // Preparare dati di test nel database
        User waiter = new User("Walter", "Beat","waiter@example.com", "securepassword", Role.WAITER);
        User cook = new User("Dean","Arrow", "cook@example.com", "faker123", Role.COOK);
        Table table = new Table(1L, 12, 1);
        Dish dish1 = new Dish(1L,"Pizza", "Delicious pizza", 10, List.of());
        Dish dish2 = new Dish(2L, "Pasta", "Tasty pasta", 8, List.of());

        userRepository.save(waiter);
        userRepository.save(cook);
        tableRepository.save(table);
        dishRepository.save(dish1);
        dishRepository.save(dish2);

        Order order = new Order(1L, LocalDateTime.now(), null, waiter, cook, table, List.of(dish1));
        orderRepository.save(order);

        Long orderId = order.getId();
        OrderRequest request = new OrderRequest(List.of(dish2.getId()), table.getId(), waiter.getId(), table.getId(), LocalDateTime.now());

        // Verificare che il metodo lanci un'eccezione per un ordine completato
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> orderService.updateOrder(orderId, request),
                "Expected updateOrder to throw ApiRequestException");

        // Verificare il messaggio dell'eccezione
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Cannot modify a completed order!", exception.getMessage());
    }

}
