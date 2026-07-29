package com.ratatouille.Ratatouille23.allergen;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ratatouille.Ratatouille23.dish.Dish;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_allergen")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "_dish_allergen",
            joinColumns = @JoinColumn(name = "allergen_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    @NonNull
    private List<Dish> dishes;

}
