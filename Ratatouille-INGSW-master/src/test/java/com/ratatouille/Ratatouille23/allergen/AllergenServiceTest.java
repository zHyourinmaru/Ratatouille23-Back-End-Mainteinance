package com.ratatouille.Ratatouille23.allergen;

import com.ratatouille.Ratatouille23.dish.Dish;
import com.ratatouille.Ratatouille23.dish.DishRepository;
import com.ratatouille.Ratatouille23.dish.DishResponse;
import com.ratatouille.Ratatouille23.dish.DishResponseMapper;
import com.ratatouille.Ratatouille23.exception.ApiRequestException;
import com.ratatouille.Ratatouille23.table.Table;
import com.ratatouille.Ratatouille23.table.TableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AllergenServiceTest {

    @Mock
    private AllergenRepository allergenRepository;
    @Mock
    private  AllergenResponseMapper allergenResponseMapper;
    @InjectMocks
    private AllergenService allergenService;

    @Test
    void testGetAllergensByAttributeTypicalCase() {
        // Caso tipico
        Long id = null;
        String name = "Lactose";
        List<String> dishNames = List.of("Pizza", "Kebab");

        // Mock del comportamento del repository
        Allergen allergen1 = new Allergen(1L,"Lactose", Collections.emptyList());
        Allergen allergen2 = new Allergen(4L, "Gluten", Collections.emptyList());


        Dish dish1 = Dish.builder().name("Pizza").allergens(List.of(allergen1, allergen2)).price(7).description("").build();
        Dish dish2 = Dish.builder().name("Kebab").allergens(List.of(allergen1)).price(7).description("").build();

        allergen1.setDishes(List.of(dish1));
        allergen2.setDishes(List.of(dish1, dish2));

        when(allergenRepository.searchAllergens(eq(name), eq(dishNames))).thenReturn(Optional.of(List.of(allergen1)));

        // Mock del comportamento del mapper
        when(allergenResponseMapper.apply(any(Allergen.class))).thenAnswer(invocation -> {
            Allergen allergen = invocation.getArgument(0);
            return new AllergenResponse(allergen.getId(), allergen.getName(), Collections.emptyList());
        });

        // Chiamata al metodo del service
        List<AllergenResponse> result = allergenService.getAllergensByAttributes(id, name, dishNames);

        // Verifica del risultato
        AllergenResponse expectedResponse = allergenResponseMapper.apply(allergen1);
        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(allergen1.getId(), expectedResponse.id());
        assertEquals(allergen1.getName(), expectedResponse.name());

        // Verifica che il metodo del repository sia stato chiamato con i parametri corretti
        verify(allergenRepository, times(1)).searchAllergens(eq(name), eq(dishNames));
    }

    @Test
    void testGetAllergensByAttributeValidID() {
        // Caso tipico
        Long id = 1L;
        String name = "Lactose";
        List<String> dishNames = List.of("Pizza", "Kebab");

        // Mock del comportamento del repository
        Allergen allergen1 = new Allergen(1L, "Lactose", Collections.emptyList());
        Allergen allergen2 = new Allergen(4L, "Gluten", Collections.emptyList());


        Dish dish1 = Dish.builder().name("Pizza").allergens(List.of(allergen1, allergen2)).price(7).description("").build();
        Dish dish2 = Dish.builder().name("Kebab").allergens(List.of(allergen1)).price(7).description("").build();

        allergen1.setDishes(List.of(dish1));
        allergen2.setDishes(List.of(dish1, dish2));

        when(allergenRepository.findById(id)).thenReturn(Optional.of(allergen1));

        // Mock del comportamento del mapper
        when(allergenResponseMapper.apply(any(Allergen.class))).thenAnswer(invocation -> {
            Allergen allergen = invocation.getArgument(0);
            return new AllergenResponse(allergen.getId(), allergen.getName(), Collections.emptyList());
        });

        // Chiamata al metodo del service
        List<AllergenResponse> result = allergenService.getAllergensByAttributes(id, name, dishNames);

        // Verifica del risultato
        AllergenResponse expectedResponse = allergenResponseMapper.apply(allergen1);
        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(allergen1.getId(), expectedResponse.id());
        assertEquals(allergen1.getName(), expectedResponse.name());

    }

    @Test
    void testGetAllergensByAttributeInvalidID() {
        // Caso tipico
        Long id = -99L;
        String name = "Lactose";
        List<String> dishNames = List.of("Pizza", "Kebab");

        // Mock del comportamento del repository
        Allergen allergen1 = new Allergen(1L, "Lactose", Collections.emptyList());
        Allergen allergen2 = new Allergen(4L, "Gluten", Collections.emptyList());


        Dish dish1 = Dish.builder().name("Pizza").allergens(List.of(allergen1, allergen2)).price(7).description("").build();
        Dish dish2 = Dish.builder().name("Kebab").allergens(List.of(allergen1)).price(7).description("").build();

        allergen1.setDishes(List.of(dish1));
        allergen2.setDishes(List.of(dish1, dish2));

        // Chiamata al metodo del service dovrebbe lanciare un'eccezione
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> allergenService.getAllergensByAttributes(id, name, dishNames),
                "Expected getTableByAttribute to throw ApiRequestException");

        // Verifica che l'eccezione abbia i parametri corretti
        assertAll("Exception details",
                () -> assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode()),
                () -> assertEquals("404 NOT_FOUND \"No allergen with id -99 found!\"", exception.getMessage()));

    }

    @Test
    void testGetAllergensByAttributeNullIDAndInvalidNameAndInvalidDishes() {
        Long id = null;
        String name = "Amid";
        List<String> dishNames = Collections.emptyList();

        // Mock del comportamento del repository
        Allergen allergen1 = new Allergen(1L, "Lactose", Collections.emptyList());
        Allergen allergen2 = new Allergen(4L, "Gluten", Collections.emptyList());

        Dish dish1 = Dish.builder().name("Pizza").allergens(List.of(allergen1, allergen2)).price(7).description("").build();
        Dish dish2 = Dish.builder().name("Kebab").allergens(List.of(allergen1)).price(7).description("").build();

        allergen1.setDishes(List.of(dish1));
        allergen2.setDishes(List.of(dish1, dish2));


        // Chiamata al metodo del service dovrebbe lanciare un'eccezione
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> allergenService.getAllergensByAttributes(id, name, dishNames),
                "Expected getTableByAttribute to throw ApiRequestException");

        // Verifica che l'eccezione abbia i parametri corretti
        assertAll("Exception details",
                () -> assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode()),
                () -> assertEquals("404 NOT_FOUND \"No allergen with this criteria found!\"", exception.getMessage()));

    }
}
