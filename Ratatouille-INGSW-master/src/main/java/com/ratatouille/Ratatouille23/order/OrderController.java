package com.ratatouille.Ratatouille23.order;

import com.ratatouille.Ratatouille23.user.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping()
    public ResponseEntity<List<OrderResponse>> getOrders(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp,
            @RequestParam(required = false) Long tableId,
            @RequestParam(required = false) Long waiterId,
            @RequestParam(required = false) Long cookId
    ) {
        return ResponseEntity.ok(service.getOrdersByParameters(id, timestamp, tableId, waiterId, cookId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderResponse>> getOrderByAttributes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer tableNumber,
            @RequestParam(required = false) String waiterName,
            @RequestParam(required = false) String cookName,
            @RequestParam(required = false) String waiterLastName,
            @RequestParam(required = false) String cookNameLastName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) List<String> dishesNames
    ) {
        return ResponseEntity.ok(service.getOrdersByAttributes(id, tableNumber, waiterName, cookName, waiterLastName, cookNameLastName, start, end , dishesNames));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countOrders(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer tableNumber,
            @RequestParam(required = false) String waiterName,
            @RequestParam(required = false) String cookName,
            @RequestParam(required = false) String waiterLastName,
            @RequestParam(required = false) String cookNameLastName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) List<String> dishesNames
    ) {
        return ResponseEntity.ok(service.countOrders(id, tableNumber, waiterName, cookName, waiterLastName, cookNameLastName, start, end , dishesNames));
    }


    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR', 'COOK')")
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<Long> completeOrder(
            @NonNull @PathVariable("orderId") Long id,
            @RequestParam(required = false) Long cookId
    ) {
        return ResponseEntity.ok(service.completeOrder(id, cookId));
    }
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(service.createOrder(request));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    @PutMapping(path = "/{orderId}")
    public void updateOrder(
            @NonNull @PathVariable("orderId") Long id,
            @RequestBody OrderRequest request) {
        service.updateOrder(id, request);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    @DeleteMapping(path = "/{userID}")
    public void deleteOrder(@NonNull @PathVariable("userID") Long id) {
        service.deleteOrder(id);
    }
}
