package com.ratatouille.Ratatouille23.order;

import com.ratatouille.Ratatouille23.dish.DishResponseMapper;
import com.ratatouille.Ratatouille23.dish.DishSmallResponse;
import com.ratatouille.Ratatouille23.dish.DishSmallResponseMapper;
import com.ratatouille.Ratatouille23.table.TableResponseMapper;
import com.ratatouille.Ratatouille23.user.UserResponseMapper;
import com.ratatouille.Ratatouille23.user.UserSmallResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderResponseMapper implements Function<Order, OrderResponse> {
    private final DishSmallResponseMapper dishSmallResponseMapper;
    private final TableResponseMapper tableResponseMapper;
    private final UserSmallResponseMapper userSmallResponseMapper;
    @Override
    public OrderResponse apply(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getStarted(),
                order.getCompleted(),
                order.getDishes()
                        .stream()
                        .map(dishSmallResponseMapper)
                        .collect(Collectors.toList()),
                tableResponseMapper.apply(order.getTable()),
                userSmallResponseMapper.apply(order.getWaiter()),
                userSmallResponseMapper.apply(order.getCook())
        );
    }
}
