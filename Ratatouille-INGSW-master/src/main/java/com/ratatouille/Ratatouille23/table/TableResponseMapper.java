package com.ratatouille.Ratatouille23.table;

import com.ratatouille.Ratatouille23.user.User;
import com.ratatouille.Ratatouille23.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class TableResponseMapper implements Function<Table, TableResponse> {
    @Override
    public TableResponse apply(Table table) {
        return new TableResponse(
                table.getId(),
                table.getNumber(),
                table.getCapacity()
        );
    }
}
