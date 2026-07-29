package com.ratatouille.Ratatouille23.dish;

import com.ratatouille.Ratatouille23.allergen.AllergenRepository;
import com.ratatouille.Ratatouille23.exception.ApiRequestException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class DishServiceTest {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private AllergenRepository allergenRepository;

    @Autowired
    private DishService dishService;

    @Test
    @Transactional
    @Rollback // Impedisce il rollback automatico della transazione alla fine del test
    void testUpdateDish() {
        // Preparare dati di test nel database
        Dish dish = Dish.builder().name("Pizza").description("Delicious pizza").price(10).allergens(List.of()).build();
        dishRepository.save(dish);

        Long dishId = dish.getId();
        DishRequest request = new DishRequest("Margherita", "Classic Margherita", 12, List.of());

        // Eseguire il metodo transazionale
        dishService.updateDish(dishId, request);

        // Verificare che le modifiche siano state apportate correttamente
        Dish updatedDish = dishRepository.findById(dishId).orElseThrow();
        assertEquals("Margherita", updatedDish.getName());
        assertEquals("Classic Margherita", updatedDish.getDescription());
        assertEquals(12, updatedDish.getPrice());
    }

    @Test
    @Transactional
    void testUpdateDishNonExistentDish() {
        Long nonExistentDishId = -1L;
        DishRequest request = new DishRequest("Margherita", "Classic Margherita", 12, List.of());

        // Verificare che il metodo lanci un'eccezione per un piatto inesistente
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> dishService.updateDish(nonExistentDishId, request),
                "Expected updateDish to throw ApiRequestException");

        // Verificare il messaggio dell'eccezione
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Dish with id -1 does not exist!", exception.getMessage());
    }
}
