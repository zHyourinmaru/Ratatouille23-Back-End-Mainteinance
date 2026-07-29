package com.ratatouille.Ratatouille23.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ratatouille.Ratatouille23.dish.DishResponse;
import com.ratatouille.Ratatouille23.dish.DishSmallResponse;
import com.ratatouille.Ratatouille23.table.TableResponse;
import com.ratatouille.Ratatouille23.user.UserResponse;
import com.ratatouille.Ratatouille23.user.UserSmallResponse;


import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime started,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime completed,
        List<DishSmallResponse> dishes,
        TableResponse table,
        UserSmallResponse waiter,
        UserSmallResponse cook
){}
