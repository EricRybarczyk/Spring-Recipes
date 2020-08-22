package dev.ericrybarczyk.springrecipes.converters;

import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    private static final Long ID = 1L;
    private static final Long ID_ALT = 2L;
    private static final String DESCRIPTION = "description";
    private static final Integer PREP_TIME = 1;
    private static final Integer COOK_TIME = 1;
    private static final Integer SERVINGS = 1;
    private static final String SOURCE = "source";
    private static final String URL = "https://www.google.com";
    private static final String DIRECTIONS = "directions";
    private static final Difficulty DIFFICULTY = Difficulty.INTERMEDIATE;
    private static final String RECIPE_NOTES = "recipe notes";
    private static final BigDecimal QUANTITY = BigDecimal.valueOf(1L);

    private RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand(),
                new NotesToNotesCommand()
        );
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void testConvert() {
        // given
        Recipe recipe = new Recipe();

        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setQuantity(QUANTITY);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ID_ALT);
        ingredient2.setDescription(DESCRIPTION);
        ingredient2.setQuantity(QUANTITY);

        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);
        Category category2 = new Category();
        category2.setId(ID_ALT);
        category2.setDescription(DESCRIPTION);

        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setNotes(notes);
        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);
        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        // when
        RecipeCommand result = converter.convert(recipe);

        // then
        assertNotNull(result);
        assertNotNull(result.getNotes());
        assertEquals(ID, result.getId());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(PREP_TIME, result.getPrepTime());
        assertEquals(COOK_TIME, result.getCookTime());
        assertEquals(SERVINGS, result.getServings());
        assertEquals(SOURCE, result.getSource());
        assertEquals(URL, result.getUrl());
        assertEquals(DIRECTIONS, result.getDirections());
        assertEquals(DIFFICULTY, result.getDifficulty());
        assertEquals(ID, result.getNotes().getId());
        assertEquals(recipe.getIngredients().size(), result.getIngredients().size());
        assertEquals(recipe.getCategories().size(), result.getCategories().size());

    }

}