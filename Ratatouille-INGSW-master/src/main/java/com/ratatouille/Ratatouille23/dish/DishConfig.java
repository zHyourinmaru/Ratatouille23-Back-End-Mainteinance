package com.ratatouille.Ratatouille23.dish;

import com.ratatouille.Ratatouille23.allergen.AllergenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DishConfig {

    @Bean
    public CommandLineRunner dishRunner(DishRepository dishRepository, AllergenRepository allergenRepository) {
        return args -> {

        };
    }

}
