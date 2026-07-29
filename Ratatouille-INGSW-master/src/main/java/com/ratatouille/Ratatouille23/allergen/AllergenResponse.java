package com.ratatouille.Ratatouille23.allergen;


import com.ratatouille.Ratatouille23.dish.DishSmallResponse;

import java.util.List;

public record AllergenResponse(
        Long id,
        String name,
        List<DishSmallResponse> containedIn
){}
