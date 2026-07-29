package com.ratatouille.Ratatouille23.allergen;

import com.ratatouille.Ratatouille23.dish.Dish;
import com.ratatouille.Ratatouille23.dish.DishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class AllergenConfig {

    @Bean
    CommandLineRunner allergenRunner(AllergenRepository allergenRepository, DishRepository dishRepository) {
        return args -> {
            Allergen a = new Allergen(
                    "Sodio",
                    new ArrayList<>()
            );

            Allergen b = new Allergen(
                    "Amido",
                    new ArrayList<>()
            );

            // DISH
            Dish c = Dish.builder()
                    .name("Carbonara")
                    .price(10)
                    .build();

            Dish d = Dish.builder()
                    .name("Margerita")
                    .price(10)
                    .build();
            dishRepository.saveAll(List.of(c, d));

            a.getDishes().add(d);
            b.getDishes().add(c);
            b.getDishes().add(d);

            allergenRepository.saveAll(List.of(a, b));

        };
    }
}
