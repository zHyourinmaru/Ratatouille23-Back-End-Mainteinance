package com.ratatouille.Ratatouille23.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, Long>, JpaSpecificationExecutor<Table> {

    Optional<Table> findByNumber(Integer number);
    Optional<List<Table>> findAllByNumberContaining(Integer number);
    Optional<List<Table>> findAllByCapacityContaining(Integer capacity);
    Optional<List<Table>> findAllByNumberContainingAndCapacityContaining(Integer number, Integer capacity);

    default Optional<List<Table>> searchTables(Integer number, Integer capacity) {
        return Optional.of(findAll(TableSpecification.searchTables(number, capacity)));
    }

    default Optional<Long> countTables(Integer number, Integer capacity) {
        return Optional.of(count(TableSpecification.searchTables(number, capacity)));
    };
}
