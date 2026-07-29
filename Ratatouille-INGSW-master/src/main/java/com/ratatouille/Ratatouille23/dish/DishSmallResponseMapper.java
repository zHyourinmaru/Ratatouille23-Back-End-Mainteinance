package com.ratatouille.Ratatouille23.dish;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DishSmallResponseMapper implements Function<Dish, DishSmallResponse> {
    @Override
    public DishSmallResponse apply(Dish dish) {
        return new DishSmallResponse(
                dish.getId(),
                dish.getName(),
                dish.getPrice()
        );
    }


}