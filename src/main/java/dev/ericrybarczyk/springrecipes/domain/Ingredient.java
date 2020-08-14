package dev.ericrybarczyk.springrecipes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
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

    public Ingredient(String description, BigDecimal quantity, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient() {

    }

}
