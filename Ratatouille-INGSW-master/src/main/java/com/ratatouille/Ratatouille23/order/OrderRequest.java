package com.ratatouille.Ratatouille23.order;

import com.ratatouille.Ratatouille23.dish.DishSmallResponse;
import com.ratatouille.Ratatouille23.table.TableResponse;
import com.ratatouille.Ratatouille23.user.UserResponse;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderRequest(
        List<Long> dishes,
        Long table,
        Long waiter,
        Long cook,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime completed
){}
