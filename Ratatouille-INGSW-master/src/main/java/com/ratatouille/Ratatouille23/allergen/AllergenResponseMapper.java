package com.ratatouille.Ratatouille23.allergen;

import com.ratatouille.Ratatouille23.dish.DishSmallResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllergenResponseMapper implements Function<Allergen, AllergenResponse> {

    private final DishSmallResponseMapper dishSmallResponseMapper;
    @Override
    public AllergenResponse apply(Allergen allergen) {
        return new AllergenResponse(
                allergen.getId(),
                allergen.getName(),
                allergen.getDishes().
                        stream()
                        .map(dishSmallResponseMapper)
                        .collect(Collectors.toList())
        );
    }
}
