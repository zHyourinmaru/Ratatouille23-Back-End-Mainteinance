package com.ratatouille.Ratatouille23.dish;

import com.ratatouille.Ratatouille23.allergen.Allergen;
import com.ratatouille.Ratatouille23.allergen.AllergenRepository;
import com.ratatouille.Ratatouille23.exception.ApiRequestException;
import com.ratatouille.Ratatouille23.order.Order;
import com.ratatouille.Ratatouille23.table.TableRequest;
import com.ratatouille.Ratatouille23.user.Role;
import com.ratatouille.Ratatouille23.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository repository;
    private final DishResponseMapper dishResponseMapper;

    private final AllergenRepository allergenRepository;

    public List<DishResponse> getDishes() {
        return repository.findAll()
                .stream()
                .map(dishResponseMapper)
                .collect(Collectors.toList());
    }

    public DishResponse getById(Long id) {
        return repository.findById(id)
                .map(dishResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "No dish with id " + id + " found!"));
    }

    public DishResponse getByName(String name) {
        return repository.findByName(name)
                .map(dishResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "No dish with id " + name + " found!"));
    }


    public List<DishResponse> getOrdersByAttributes(Long id, String name, Integer price, List<String> allergenNames) {
        if (id != null) {
            return Collections.singletonList(getById(id));
        } else {
            return repository.searchDishes(name, price, allergenNames)
                    .filter(list -> !list.isEmpty())
                    .map(list -> list
                            .stream()
                            .map(dishResponseMapper)
                            .collect(Collectors.toList()))
                    .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "no dishes with this criteria found!"));
        }
    }

    public Long createDish(DishRequest request) {
        Optional<Dish> dishOptional = repository.findByName(request.name());
        if (dishOptional.isPresent()) {
            throw new ApiRequestException(HttpStatus.BAD_REQUEST,"Name taken!");
        }
        Dish dish = Dish.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .allergens(allergenRepository.findAllById(request.allergensID()))
                .build();
        return repository.save(dish).getId();
    }

    public void deleteDish(Long id) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Dish with id " + id + " does not exists!");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void updateDish(Long id, DishRequest request) {
        Dish dish = repository.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Dish with id " + id + " does not exists!"));
        String name = request.name();
        String description = request.description();
        Integer price = request.price();
        List<Allergen> allergens = allergenRepository.findAllById(request.allergensID());

        if (name != null && name.length() > 0 && !Objects.equals(dish.getName(), name)) {
            Optional<Dish> dishOptional = repository.findByName(name);
            if (dishOptional.isPresent()) {
                throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Name taken");
            }
            dish.setName(name);
        }

        if (description != null && description.length() > 0 && !Objects.equals(dish.getDescription(), description)) {
            dish.setDescription(description);
        }

        if (price != null && price >= 0 && !Objects.equals(dish.getPrice(), price)) {
            dish.setPrice(price);
        }

        if (!allergens.isEmpty() && !Objects.equals(dish.getAllergens(), allergens)) {
            dish.setAllergens(allergens);
        }
    }
}
