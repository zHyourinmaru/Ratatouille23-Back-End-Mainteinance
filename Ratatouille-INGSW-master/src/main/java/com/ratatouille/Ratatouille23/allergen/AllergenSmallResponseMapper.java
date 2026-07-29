package com.ratatouille.Ratatouille23.allergen;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AllergenSmallResponseMapper implements Function<Allergen, AllergenSmallResponse> {

    @Override
    public AllergenSmallResponse apply(Allergen allergen) {
        return new AllergenSmallResponse(
                allergen.getId(),
                allergen.getName()
        );
    }
}
