package dev.ericrybarczyk.springrecipes.converters;

import dev.ericrybarczyk.springrecipes.commands.CategoryCommand;
import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;
import dev.ericrybarczyk.springrecipes.commands.NotesCommand;
import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.domain.Difficulty;
import dev.ericrybarczyk.springrecipes.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    private static final Long ID = 1L;
    private static final Long ID_ALT = 2L;
    private static final String DESCRIPTION = "description";
    private static final Integer PREP_TIME = 1;
    private static final Integer COOK_TIME = 1;
    private static final Integer SERVINGS = 1;
    private static final String SOURCE = "source";
    private static final String URL = "https://www.google.com";
    private static final String DIRECTIONS = "directions";
    private static final Difficulty DIFFICULTY = Difficulty.ADVANCED;
    private static final String RECIPE_NOTES = "recipe notes";
    private static final BigDecimal QUANTITY = BigDecimal.valueOf(1L);

    private RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory(),
                new NotesCommandToNotes()
        );
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void testConvert() {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setQuantity(QUANTITY);
        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(ID_ALT);
        ingredientCommand2.setDescription(DESCRIPTION);
        ingredientCommand2.setQuantity(QUANTITY);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);
        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(ID_ALT);
        categoryCommand2.setDescription(DESCRIPTION);

        recipeCommand.setId(ID);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setNotes(notesCommand);
        recipeCommand.getIngredients().add(ingredientCommand);
        recipeCommand.getIngredients().add(ingredientCommand2);
        recipeCommand.getCategories().add(categoryCommand);
        recipeCommand.getCategories().add(categoryCommand2);

        // when
        Recipe result = converter.convert(recipeCommand);

        // then
        assertNotNull(result);
        assertNotNull(result.getNotes());
        assertEquals(ID, result.getId());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(PREP_TIME, result.getPrepTime());
        assertEquals(COOK_TIME, result.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, result.getSource());
        assertEquals(URL, result.getUrl());
        assertEquals(DIRECTIONS, result.getDirections());
        assertEquals(DIFFICULTY, result.getDifficulty());
        assertEquals(ID, result.getNotes().getId());
        assertEquals(recipeCommand.getIngredients().size(), result.getIngredients().size());
        assertEquals(recipeCommand.getCategories().size(), result.getCategories().size());
    }

}