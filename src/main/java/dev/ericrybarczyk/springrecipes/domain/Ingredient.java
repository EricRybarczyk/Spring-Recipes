package dev.ericrybarczyk.springrecipes.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal quantity;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    @ManyToOne
    private Recipe recipe;

    public Ingredient() {

    }

    public Ingredient(String description, BigDecimal quantity, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient(String description, BigDecimal quantity, UnitOfMeasure unitOfMeasure, Recipe recipe) {
        this.description = description;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
        this.recipe = recipe;
    }


}
