package dev.ericrybarczyk.springrecipes.converters;

import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;
import dev.ericrybarczyk.springrecipes.commands.UnitOfMeasureCommand;
import dev.ericrybarczyk.springrecipes.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";
    private static final BigDecimal QUANTITY = BigDecimal.valueOf(1L);

    private IngredientCommandToIngredient converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void testConvert() {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);
        ingredientCommand.setId(ID);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);
        ingredientCommand.setQuantity(QUANTITY);

        // when
        Ingredient result = converter.convert(ingredientCommand);

        //then
        assertNotNull(result);
        assertEquals(ID, result.getId());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(QUANTITY, result.getQuantity());

    }

    @Test
    public void testConvertWithNullUnitOfMeasureCommand() {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setQuantity(QUANTITY);

        // when
        Ingredient result = converter.convert(ingredientCommand);

        // then
        assertNotNull(result);
        assertNull(result.getUnitOfMeasure());
        assertEquals(ID, result.getId());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(QUANTITY, result.getQuantity());

    }

}