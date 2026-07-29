package com.ratatouille.Ratatouille23.user;

import com.ratatouille.Ratatouille23.order.OrderResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PreAuthorize(value = "hasAuthority('ADMIN')") // importantissimo hasRole non funziona, solo auhtorities.
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email
    ) {
        if (id != null) {
            return ResponseEntity.ok(List.of(service.getById(id)));
        } else if (firstName != null) {
            return ResponseEntity.ok(List.of(service.getByFirstName(firstName)));
        } else if (lastName != null) {
            return ResponseEntity.ok(List.of(service.getByLastName(lastName)));
        } else if (email != null) {
            return ResponseEntity.ok(List.of(service.getByEmail(email)));
        } else {
            return ResponseEntity.ok(service.getUsers());
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> researchUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role
    ) {
        return ResponseEntity.ok(service.getUsersByAttributes(id, firstName, lastName, email, role));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role
    ) {
        return ResponseEntity.ok(service.countUsers(id, firstName, lastName, email, role));
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrderByUser(
            @PathVariable("userId") Long id
    ) {
        return ResponseEntity.ok(service.getOrderByUser(id));
    }
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<Long> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(service.createUser(request));
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping(path = "/{userID}")
    public void deleteUser(@NonNull @PathVariable("userID") Long id) {
        service.deleteUser(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/{userID}")
    public void updateUser(
            @NonNull @PathVariable("userID") Long id,
            @RequestBody UserRequest request
    ) {
        service.updateUser(id, request);
    }

}
