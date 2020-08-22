package dev.ericrybarczyk.springrecipes.converters;

import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;
import dev.ericrybarczyk.springrecipes.domain.Ingredient;
import dev.ericrybarczyk.springrecipes.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class IngredientToIngredientCommandTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";
    private static final BigDecimal QUANTITY = BigDecimal.valueOf(1L);

    private IngredientToIngredientCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void testConvert() {
        // given
        Ingredient ingredient = new Ingredient();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setDescription(DESCRIPTION);
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setQuantity(QUANTITY);
        ingredient.setUnitOfMeasure(unitOfMeasure);

        // when
        IngredientCommand result = converter.convert(ingredient);

        // then
        assertNotNull(result);
        assertEquals(ID, result.getId());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(QUANTITY, result.getQuantity());
    }

    @Test
    public void testConvertWithNullUnitOfMeasure() {
        // given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setQuantity(QUANTITY);

        // when
        IngredientCommand result = converter.convert(ingredient);

        // then
        assertNotNull(result);
        assertNull(result.getUnitOfMeasure());
        assertEquals(ID, result.getId());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(QUANTITY, result.getQuantity());
    }

}