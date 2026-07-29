package com.ratatouille.Ratatouille23.table;

import com.ratatouille.Ratatouille23.exception.ApiRequestException;
import com.ratatouille.Ratatouille23.user.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository repository;
    private final TableResponseMapper tableResponseMapper;



    public List<TableResponse> getTables() {
        return repository.findAll()
                .stream()
                .map(tableResponseMapper)
                .collect(Collectors.toList());
    }


    public TableResponse getTableById(Long id)     {
        return repository.findById(id)
                .map(tableResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Table with id " + id + " does not exists!"));
    }

    public TableResponse getTableByNumber(Integer number) {
        return repository.findByNumber(number)
                .map(tableResponseMapper)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Table with number " + number + " does not exists!"));
    }



    public List<TableResponse> getTableByAttribute(Long id, Integer number, Integer capacity) {
        if (id != null) {
            return Collections.singletonList(getTableById(id));
        }
        return repository.searchTables(number, capacity)
                .filter(list -> !list.isEmpty())
                .map(list -> list
                        .stream()
                        .map(tableResponseMapper)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "No table with this criteria found!"));
    }

    public Long createTable(TableRequest request) {
        Optional<Table> tableOptional = repository.findByNumber(request.number());
        if (tableOptional.isPresent()) {
            throw new ApiRequestException(HttpStatus.BAD_REQUEST,"this table number already exists!");
        }
        Table table = Table.builder()
                .number(request.number())
                .capacity(request.capacity())
                .build();
        return repository.save(table).getId();
    }

    public Long countTables(Long id, Integer number, Integer capacity) {
        if (id != null) {
            return Stream.of(getTableById(id)).count();
        }
        return repository.countTables(number, capacity)
                .orElseThrow(() ->  new ApiRequestException(HttpStatus.NOT_FOUND, "No table with this criteria found!"));
    }


    @Transactional
    public void updateTable(Long id, TableRequest request) {
        Table table = repository.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Table with id " + id + " does not exists!"));
        Integer number = request.number();
        Integer capacity = request.capacity();

        if (capacity != null && !Objects.equals(table.getCapacity(), capacity)) {
            table.setCapacity(capacity);
        }
        if (number != null && !Objects.equals(table.getNumber(), number)) {
            Optional<Table> tableOptional = repository.findByNumber(number);
            if (tableOptional.isPresent()) {
                throw new ApiRequestException(HttpStatus.BAD_REQUEST, "this number already exists");
            }
            table.setNumber(number);
        }
    }

    public void deleteTable(Long id) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Table with id " + id + " does not exists!");
        }
        repository.deleteById(id);
    }
}
