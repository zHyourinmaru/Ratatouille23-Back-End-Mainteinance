package com.ratatouille.Ratatouille23.order;

import com.ratatouille.Ratatouille23.allergen.Allergen;
import com.ratatouille.Ratatouille23.dish.Dish;
import com.ratatouille.Ratatouille23.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @CreationTimestamp
    @Column(updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime started;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime completed;

    @NonNull
    @Check(constraints = "waiter.role == 'WAITER'")
    @JoinColumn(name="waiter_id", updatable = false)
    @ManyToOne
    private User waiter;

    @Check(constraints = "cook.role == 'COOK'")
    @JoinColumn(name = "cook_id", updatable = false)
    @ManyToOne
    private User cook;


    @NonNull
    @JoinColumn(name = "table_id")
    @ManyToOne
    private com.ratatouille.Ratatouille23.table.Table table;

    @NonNull
    @ManyToMany
    private List<Dish> dishes;

}
