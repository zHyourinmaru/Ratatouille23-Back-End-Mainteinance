package com.ratatouille.Ratatouille23.allergen;

import com.ratatouille.Ratatouille23.dish.DishRequest;
import com.ratatouille.Ratatouille23.dish.DishResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/allergen")
@RequiredArgsConstructor
public class AllergenController {

    private final AllergenService service;

    @GetMapping()
    public ResponseEntity<List<AllergenResponse>> getAllergens(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name
    ) {
        if (id != null) {
            return ResponseEntity.ok(List.of(service.getById(id)));
        } else if (name != null) {
            return ResponseEntity.ok(List.of(service.getByName(name)));
        } else {
            return ResponseEntity.ok(service.getAllergens());
        }
    }
    // TODO: piatti che contengono specifico allergene

    @GetMapping("{allergenId}/dishes")
    public ResponseEntity<List<DishResponse>> getDishesByAllergen(
            @PathVariable(value="allergenId") Long id
    ) {
        return ResponseEntity.ok(service.getDishesByAllergen(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AllergenResponse>> researchAllergen(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, value = "dishes") List<String> dishesNames
    ) {
        return ResponseEntity.ok(service.getAllergensByAttributes(id, name, dishesNames));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping()
    public ResponseEntity<Long> createAllergen(@RequestBody AllergenRequest request) {
        return ResponseEntity.ok(service.createAllergen(request));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(path = "/{allergenId}")
    public void deleteAllergen(@NonNull @PathVariable("allergenId") Long id) {
        service.deleteAllergen(id);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PutMapping(path = "/{allergenId}")
    public void updateAllergen(
            @NonNull @PathVariable("allergenId") Long id,
            @RequestBody AllergenRequest request) {
        service.updateAllergen(id, request);
    }

}
