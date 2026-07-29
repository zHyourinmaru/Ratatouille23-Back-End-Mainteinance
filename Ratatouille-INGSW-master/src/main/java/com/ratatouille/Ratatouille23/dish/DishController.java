package com.ratatouille.Ratatouille23.dish;

import com.ratatouille.Ratatouille23.table.TableRequest;
import com.ratatouille.Ratatouille23.user.UserRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishController {
    private final DishService service;

    @GetMapping
    public ResponseEntity<List<DishResponse>> getDishes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name
    ) {
        if (id != null) {
            return ResponseEntity.ok(List.of(service.getById(id)));
        } else if (name != null) {
            return ResponseEntity.ok(List.of(service.getByName(name)));
        } else {
            return ResponseEntity.ok(service.getDishes());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DishResponse>> getDishes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) List<String> allergenNames
    ) {
        return ResponseEntity.ok(service.getOrdersByAttributes(id, name, price, allergenNames));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping()
    public ResponseEntity<Long> createDish(@RequestBody DishRequest request) {
        return ResponseEntity.ok(service.createDish(request));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(path = "/{dishId}")
    public void deleteDish(@NonNull @PathVariable("dishId") Long id) {
        service.deleteDish(id);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PutMapping(path = "/{dishId}")
    public void updateDish(
            @NonNull @PathVariable("dishId") Long id,
            @RequestBody DishRequest request) {
        service.updateDish(id, request);
    }
}
