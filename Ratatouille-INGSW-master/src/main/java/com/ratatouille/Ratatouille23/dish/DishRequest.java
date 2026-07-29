package com.ratatouille.Ratatouille23.dish;

import java.util.List;

public record DishRequest(
        String name,
        String description,
        Integer price,
        List<Long> allergensID
){}
