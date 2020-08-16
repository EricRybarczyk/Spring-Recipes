package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
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
}