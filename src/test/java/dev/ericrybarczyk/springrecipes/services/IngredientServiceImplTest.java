package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;
import dev.ericrybarczyk.springrecipes.converters.IngredientCommandToIngredient;
import dev.ericrybarczyk.springrecipes.converters.IngredientToIngredientCommand;
import dev.ericrybarczyk.springrecipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dev.ericrybarczyk.springrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.ericrybarczyk.springrecipes.domain.Ingredient;
import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.repositories.RecipeRepository;
import dev.ericrybarczyk.springrecipes.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class IngredientServiceImplTest {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID_1 = 1L;
    private static final Long INGREDIENT_ID_2 = 2L;
    private static final Long INGREDIENT_ID_3 = 3L;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientService ingredientService;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
    }

    @Test
    public void testFindByRecipeIdAndIngredientId() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID_1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID_2);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(INGREDIENT_ID_3);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(optionalRecipe);

        // when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID_3);

        // then
        assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
        assertEquals(INGREDIENT_ID_3, ingredientCommand.getId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
    }


    @Test
    public void testSaveIngredientCommand() {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID_1);
        ingredientCommand.setRecipeId(RECIPE_ID);

        Optional<Recipe> optionalRecipe = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(INGREDIENT_ID_1);

        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(optionalRecipe);
        Mockito.when(recipeRepository.save(ArgumentMatchers.any())).thenReturn(savedRecipe);

        // when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        // then
        assertEquals(INGREDIENT_ID_1, savedIngredientCommand.getId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(recipeRepository, Mockito.times(1)).save(ArgumentMatchers.any(Recipe.class));
    }

}