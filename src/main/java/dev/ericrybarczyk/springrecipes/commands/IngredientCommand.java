package dev.ericrybarczyk.springrecipes.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal quantity;
    private UnitOfMeasureCommand unitOfMeasure;
}
