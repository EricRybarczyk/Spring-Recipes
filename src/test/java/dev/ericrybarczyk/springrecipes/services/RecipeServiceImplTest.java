package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.converters.*;
import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.exceptions.NotFoundException;
import dev.ericrybarczyk.springrecipes.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RecipeCommandToRecipe recipeCommandToRecipe = new RecipeCommandToRecipe(
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory(),
                new NotesCommandToNotes()
        );
        RecipeToRecipeCommand recipeToRecipeCommand = new RecipeToRecipeCommand(
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand(),
                new NotesToNotesCommand()
        );
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipes() {
        // define data for the mock to use
        Set<Recipe> recipeData = new HashSet<>();
        Recipe recipe = new Recipe();
        recipeData.add(recipe);

        // set up the mock response for the repository that was mocked for us by Mockito
        Mockito.when(recipeRepository.findAll()).thenReturn(recipeData);

        // test the service, which will use the mock repository
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(optionalRecipe);

        Optional<Recipe> result = recipeService.findById(1L);

        assertTrue("Expected a value but the Optional was Empty", result.isPresent());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(recipeRepository, Mockito.never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void testGetRecipeByIdAndNotFound() throws Exception {
        Optional<Recipe> RECIPE_OPTIONAL_EMPTY = Optional.empty();
        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(RECIPE_OPTIONAL_EMPTY);
        // this call should raise the exception
        Optional<Recipe> optionalRecipeReturned = recipeService.findById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void testGetRecipeByCommandAndNotFound() throws Exception {
        // given
        Optional<Recipe> RECIPE_OPTIONAL_EMPTY = Optional.empty();
        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(RECIPE_OPTIONAL_EMPTY);
        // this call should raise the exception
        RecipeCommand recipeCommand = recipeService.findCommandById(1L);
    }

    @Test
    public void testDeleteById() {
        // given
        Long idToDelete = 2L;

        // when
        recipeService.deleteById(idToDelete);

        // then
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }

}