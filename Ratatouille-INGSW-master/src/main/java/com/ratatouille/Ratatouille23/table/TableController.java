package com.ratatouille.Ratatouille23.table;

import com.ratatouille.Ratatouille23.user.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/table")
@RequiredArgsConstructor
public class TableController {

    private final TableService service;

    @GetMapping()
    public ResponseEntity<List<TableResponse>> getTables(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer number
    ) {
        if (id != null) {
            return ResponseEntity.ok(List.of(service.getTableById(id)));
        } else if (number != null) {
            return ResponseEntity.ok(List.of(service.getTableByNumber(number)));
        } else {
            return ResponseEntity.ok(service.getTables());
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<TableResponse>> getTableByAttributes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) Integer capacity) {

        return ResponseEntity.ok(service.getTableByAttribute(id, number, capacity));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countTables(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) Integer capacity
    ) {
        return ResponseEntity.ok(service.countTables(id, number, capacity));
    }


    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping()
    public void createTable(@RequestBody TableRequest request) {
        service.createTable(request);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(path = "/{tableId}")
    public void deleteUser(@NonNull @PathVariable("tableId") Long id) {
        service.deleteTable(id);
    }
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PutMapping(path = "/{tableID}")
    public void updateTable(
            @NonNull @PathVariable("tableID") Long id,
            @RequestBody TableRequest tableRequest
    ) {
        service.updateTable(id, tableRequest);
    }

}
