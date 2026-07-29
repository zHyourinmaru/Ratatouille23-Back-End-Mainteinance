package com.ratatouille.Ratatouille23.allergen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long>, JpaSpecificationExecutor<Allergen> {
    Optional<Allergen> findByName(String name);
    default Optional<List<Allergen>> searchAllergens(String name, List<String > dishesNames) {
        return Optional.of(findAll(AllergenSpecification.searchAllergens(name, dishesNames)));
    }

}
