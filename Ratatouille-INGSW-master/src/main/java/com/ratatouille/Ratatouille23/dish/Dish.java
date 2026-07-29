package com.ratatouille.Ratatouille23.dish;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ratatouille.Ratatouille23.allergen.Allergen;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "_dish")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;
    private String description;

    @NonNull
    @Column(nullable = false)
    private Integer price;

    @ManyToMany(mappedBy = "dishes")
    @JsonIgnore
    private List<Allergen> allergens;
}
