package com.ratatouille.Ratatouille23.allergen;

import com.ratatouille.Ratatouille23.dish.Dish;
import com.ratatouille.Ratatouille23.dish.DishRepository;
import com.ratatouille.Ratatouille23.dish.DishResponse;
import com.ratatouille.Ratatouille23.dish.DishResponseMapper;
import com.ratatouille.Ratatouille23.exception.ApiRequestException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllergenService {

    private final AllergenRepository repository;
    private final AllergenResponseMapper allergenResponseMapper;
    private final DishRepository dishRepository;
    private final DishResponseMapper dishResponseMapper;

    public List<AllergenResponse> getAllergens() {
        return repository.findAll()
                .stream()
                .map(allergenResponseMapper)
                .collect(Collectors.toList());
    }

    public AllergenResponse getById(Long id) {
        return repository.findById(id)
                .map(allergenResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "No allergen with id " + id + " found!" ));
    }

    public AllergenResponse getByName(String name) {
        return repository.findByName(name)
                .map(allergenResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "No allergen with name " + name + " found!" ));
    }



    public void addAllergen(AllergenRequest request) {
        Optional<Allergen> userOptional = repository.findByName(request.name());
        if (userOptional.isPresent()) {
            throw new ApiRequestException(HttpStatus.BAD_REQUEST,"Allergen with name " + request.name() + "already exists");
        }
        Allergen allergen = Allergen.builder()
                .name(request.name())
                .build();

        repository.save(allergen);
    }


    public List<AllergenResponse> getAllergensByAttributes(Long id, String name, List<String> dishesNames) {
        if (id != null) {
            return Collections.singletonList(getById(id));
        }
        return repository.searchAllergens(name, dishesNames)
                .filter(list -> !list.isEmpty())
                .map(list -> list
                        .stream()
                        .map(allergenResponseMapper)
                        .collect(Collectors.toList()))
                .orElseThrow(() ->  new ApiRequestException(HttpStatus.NOT_FOUND, "No allergen with this criteria found!"));
    }


    public List<DishResponse> getDishesByAllergen(Long id) {
        return dishRepository.findAllByAllergensId(id)
                .filter(list -> !list.isEmpty())
                .map(list -> list
                        .stream()
                        .map(dishResponseMapper)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "No dishes with this criteria found!"));
    }

    public Long createAllergen(AllergenRequest request) {
        Optional<Allergen> allergenOptional = repository.findByName(request.name());
        if (allergenOptional.isPresent()) {
            throw new ApiRequestException(HttpStatus.BAD_REQUEST,"Name taken!");
        }
        Allergen allergen = Allergen.builder()
                .name(request.name())
                .build();

        return repository.save(allergen).getId();
    }

    @Transactional
    public void updateAllergen(Long id, AllergenRequest request) {
        Allergen allergen = repository.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND,
                "Allergen with id " + id + " does not exists!"));
        String name = request.name();

        if (name != null && name.length() > 0 && !Objects.equals(allergen.getName(), name)) {
            Optional<Allergen> allergenOptional = repository.findByName(name);
            if (allergenOptional.isPresent()) {
                throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Name taken");
            }
            allergen.setName(name);
        }
    }

    public void deleteAllergen(Long id) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Allergen with id " + id + " does not exists!");
        }
        repository.deleteById(id);
    }
}
