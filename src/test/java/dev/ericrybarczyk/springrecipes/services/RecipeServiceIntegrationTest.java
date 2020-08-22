package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.converters.RecipeCommandToRecipe;
import dev.ericrybarczyk.springrecipes.converters.RecipeToRecipeCommand;
import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIntegrationTest {

    private static final String MODIFIED_DESCRIPTION = "modified description";

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    @Transactional // needed because we are manipulating collection properties which are lazy load, else we get LazyInitializationException
    public void testSaveModifiedDescription() {
        // given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe sourceRecipe = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(sourceRecipe);

        assertNotNull(recipeCommand);

        // when
        recipeCommand.setDescription(MODIFIED_DESCRIPTION);
        RecipeCommand result = recipeService.saveRecipeCommand(recipeCommand);

        // then
        assertEquals(MODIFIED_DESCRIPTION, result.getDescription());
        assertEquals(sourceRecipe.getId(), result.getId());
        assertEquals(sourceRecipe.getDirections(), result.getDirections());
        assertEquals(sourceRecipe.getCategories().size(), result.getCategories().size());
        assertEquals(sourceRecipe.getIngredients().size(), result.getIngredients().size());
    }

}
