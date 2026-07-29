package com.ratatouille.Ratatouille23.table;


import com.ratatouille.Ratatouille23.exception.ApiRequestException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TableServiceTest {
    @Mock
    private TableRepository tableRepository;
    @Mock
    private TableResponseMapper tableResponseMapper;

    @InjectMocks
    private TableService tableService;

    @Test
    void testGetTableByAttributeTypicalCase() {
        // Caso tipico
        Long id = null;
        Integer number = 4;
        Integer capacity = 6;

        // Mock del comportamento del repository
        List<Table> mockTables = Arrays.asList(
                new Table(1L, 1, 6),
                new Table(2L, 3, 6),
                new Table(3L, 4, 6)
        );
        when(tableRepository.searchTables(eq(number), eq(capacity))).thenReturn(Optional.of(List.of(mockTables.get(2))));

        // Mock del comportamento del mapper
        when(tableResponseMapper.apply(any(Table.class))).thenAnswer(invocation -> {
            Table table = invocation.getArgument(0);
            return new TableResponse(table.getId(), table.getNumber(), table.getCapacity());
        });


        // Chiamata al metodo del service
        List<TableResponse> result = tableService.getTableByAttribute(id, number, capacity);

        // Verifica del risultato
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verifica che il metodo del repository sia stato chiamato con i parametri corretti
        verify(tableRepository, times(1)).searchTables(eq(number), eq(capacity));
    }

    @Test
    void testGetTableByAttributeWithValidID() {
        // Caso tipico
        Long id = 1L;
        Integer number = 12;
        Integer capacity = 3;

        // Mock del comportamento del repository
        List<Table> mockTables = Arrays.asList(
                new Table(1L, 1, 6),
                new Table(2L, 3, 6),
                new Table(3L, 4, 6)
        );

        when(tableRepository.findById(id)).thenReturn(Optional.of(mockTables.get(0)));

        // Mock del comportamento del mapper
        when(tableResponseMapper.apply(any(Table.class))).thenAnswer(invocation -> {
            Table table = invocation.getArgument(0);
            return new TableResponse(table.getId(), table.getNumber(), table.getCapacity());
        });

        // Chiamata al metodo del service
        List<TableResponse> result = tableService.getTableByAttribute(id, number, capacity);

        // Verifica del risultato
        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(1L, result.stream().findFirst().get().id());
    }

    @Test
    void testGetTableByAttributeLowerBoundaryCase() {
        // Caso limite inferiore
        Integer number = Integer.MIN_VALUE;
        Integer capacity = 6;


        // Mock del comportamento del repository
        List<Table> mockTables = Arrays.asList(
                new Table(1L, number, 6),
                new Table(2L, 12, 6),
                new Table(3L, 33, 6)
        );
        when(tableRepository.searchTables(eq(number), eq(capacity))).thenReturn(Optional.of(List.of(mockTables.get(0))));

        // Chiamata al metodo del service
        List<TableResponse> result = tableService.getTableByAttribute(null, number, capacity);

        // Verifica del risultato
        assertNotNull(result);
        assertEquals(1, result.size());


        // Verifica che il metodo del repository sia stato chiamato con i parametri corretti
        verify(tableRepository, times(1)).searchTables(eq(number), eq(capacity));
    }

    @Test
    void testGetTableByAttributeUpperBoundaryCase() {
        // Caso limite superiore
        Integer number = Integer.MAX_VALUE;
        Integer capacity = 6;

        // Mock del comportamento del repository
        List<Table> mockTables = Arrays.asList(
                new Table(1L, number, 6),
                new Table(2L, 12, 6),
                new Table(3L, 33, 6)
        );
        when(tableRepository.searchTables(eq(number), eq(capacity))).thenReturn(Optional.of(List.of(mockTables.get(0))));

        // Mock del comportamento del mapper
        when(tableResponseMapper.apply(any(Table.class))).thenAnswer(invocation -> {
            Table table = invocation.getArgument(0);
            return new TableResponse(table.getId(), table.getNumber(), table.getCapacity());
        });

        // Chiamata al metodo del service
        List<TableResponse> result = tableService.getTableByAttribute(null, number, capacity);

        // Verifica del risultato
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verifica che il metodo del repository sia stato chiamato con i parametri corretti
        verify(tableRepository, times(1)).searchTables(eq(number), eq(capacity));
    }


    @Test
    void testGetTableByAttributeWithNullNumber() {
        // Caso limite superiore
        Integer number = null;
        Integer capacity = 6;

        // Mock del comportamento del repository
        List<Table> mockTables = Arrays.asList(
                new Table(1L, 1, 6),
                new Table(2L, 12, 6),
                new Table(3L, 33, 2)
        );
        when(tableRepository.searchTables(eq(number), eq(capacity))).thenReturn(Optional.of(List.of(mockTables.get(0), mockTables.get(1))));

        // Mock del comportamento del mapper
        when(tableResponseMapper.apply(any(Table.class))).thenAnswer(invocation -> {
            Table table = invocation.getArgument(0);
            return new TableResponse(table.getId(), table.getNumber(), table.getCapacity());
        });

        // Chiamata al metodo del service
        List<TableResponse> result = tableService.getTableByAttribute(null, number, capacity);

        // Verifica del risultato
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verifica che il metodo del repository sia stato chiamato con i parametri corretti
        verify(tableRepository, times(1)).searchTables(eq(number), eq(capacity));
    }

    @Test
    void testGetTableByAttributeInvalidResult() {
        // Caso limite superiore
        Integer number = 12;
        Integer capacity = 34;

        // Mock del comportamento del repository
        List<Table> mockTables = Arrays.asList(
                new Table(1L, 1, 6),
                new Table(2L, 12, 6),
                new Table(3L, 33, 2)
        );
        when(tableRepository.searchTables(eq(number), eq(capacity))).thenReturn(Optional.of(Collections.emptyList()));

        // Mock del comportamento del mapper
        when(tableResponseMapper.apply(any(Table.class))).thenAnswer(invocation -> {
            Table table = invocation.getArgument(0);
            return new TableResponse(table.getId(), table.getNumber(), table.getCapacity());
        });

        // Chiamata al metodo del service dovrebbe lanciare un'eccezione
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tableService.getTableByAttribute(null, number, capacity),
                "Expected getTableByAttribute to throw ApiRequestException");

        // Verifica che l'eccezione abbia i parametri corretti
        assertAll("Exception details",
                () -> assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode()),
                () -> assertEquals("404 NOT_FOUND \"No table with this criteria found!\"", exception.getMessage()));

        // Verifica che il metodo del repository sia stato chiamato con i parametri corretti
        verify(tableRepository, times(1)).searchTables(eq(number), eq(capacity));
    }


    @Test
    void testGetTableByAttributeInvalidID() {
        // Caso limite superiore
        Long id = -1L;
        Integer number = 12;
        Integer capacity = 11;

        // Chiamata al metodo del service dovrebbe lanciare un'eccezione
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tableService.getTableByAttribute(id, number, capacity),
                "Expected getTableByAttribute to throw ApiRequestException");

        // Verifica che l'eccezione abbia i parametri corretti
        assertAll("Exception details",
                () -> assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode()),
                () -> assertEquals("404 NOT_FOUND \"Table with id -1 does not exists!\"", exception.getMessage()));

    }
    @Test
    void testGetTableByAttributeWithNullNumberAndNullCapacity() {
        // Caso limite superiore
        Integer number = null;
        Integer capacity = null;

        when(tableRepository.searchTables(eq(number), eq(capacity))).thenReturn(Optional.of(Collections.emptyList()));

        // Chiamata al metodo del service dovrebbe lanciare un'eccezione
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tableService.getTableByAttribute(null, number, capacity),
                "Expected getTableByAttribute to throw ApiRequestException");

        // Verifica che l'eccezione abbia i parametri corretti
        assertAll("Exception details",
                () -> assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode()),
                () -> assertEquals("404 NOT_FOUND \"No table with this criteria found!\"", exception.getMessage()));


        // Verifica che il metodo del repository sia stato chiamato con i parametri corretti
        verify(tableRepository, times(1)).searchTables(eq(number), eq(capacity));
    }

}

