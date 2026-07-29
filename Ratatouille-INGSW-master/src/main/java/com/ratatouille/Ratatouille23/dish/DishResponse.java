package com.ratatouille.Ratatouille23.dish;

import com.ratatouille.Ratatouille23.allergen.AllergenSmallResponse;

import java.util.List;

public record DishResponse(
        Long id,
        String name,
        String description,
        Integer price,
        List<AllergenSmallResponse> contains
){}
