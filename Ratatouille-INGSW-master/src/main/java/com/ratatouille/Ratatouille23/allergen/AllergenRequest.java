package com.ratatouille.Ratatouille23.allergen;

import com.ratatouille.Ratatouille23.dish.Dish;

import java.util.List;


public record AllergenRequest(
        String name,
        List<String> dishesNames
){}
