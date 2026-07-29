package com.ratatouille.Ratatouille23.table;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@jakarta.persistence.Table(name="_table", uniqueConstraints = {
        @UniqueConstraint(name = "table_number_unique", columnNames = "number")
})
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    private Integer number;

    @NonNull
    @Column(nullable = false)
    private Integer capacity;

}
