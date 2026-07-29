package com.ratatouille.Ratatouille23.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long>, JpaSpecificationExecutor<Dish> {
    Optional<Dish> findByName(String name);

    default Optional<List<Dish>> searchDishes(String name, Integer price, List<String> allergenNames) {
        return Optional.of(findAll(DishSpecification.searchDishes(name, price, allergenNames)));
    }

    Optional<List<Dish>> findAllByAllergensId(Long id);
}
