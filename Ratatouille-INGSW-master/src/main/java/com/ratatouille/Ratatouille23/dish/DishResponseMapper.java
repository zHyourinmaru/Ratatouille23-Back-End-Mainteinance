package com.ratatouille.Ratatouille23.dish;

import com.ratatouille.Ratatouille23.allergen.AllergenSmallResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishResponseMapper implements Function<Dish, DishResponse> {

    private final AllergenSmallResponseMapper allergenSmallResponseMapper;

    @Override
    public DishResponse apply(Dish dish) {
        return new DishResponse(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getAllergens()
                        .stream()
                        .map(allergenSmallResponseMapper)
                        .collect(Collectors.toList())
        );
    }




}
