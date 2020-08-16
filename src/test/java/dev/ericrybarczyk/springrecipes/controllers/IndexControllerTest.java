package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

public class IndexControllerTest {

    public static final String EXPECTED_INDEX_VIEW_NAME = "index";
    public static final String EXPECTED_MODEL_ATTRIBUTE_NAME = "recipes";

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;
    @Captor // define the argument captor this was is better for use with generics like the Set<T> here
    private ArgumentCaptor<Set<Recipe>> argumentCaptor;

    private IndexController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(recipeService);
        //argumentCaptor = ArgumentCaptor.forClass(Set.class);
    }

    @Test
    public void getIndexPage() {
        // given
        Set<Recipe> recipes = new HashSet<>();
        // we need different Id value so the objects are distinct for equality and both get added to the Set
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipes.add(recipe1);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe2);

        Mockito.when(recipeService.getRecipes()).thenReturn(recipes);
        //ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        String viewName = controller.getIndexPage(model);

        // then
        assertEquals(EXPECTED_INDEX_VIEW_NAME, viewName);
        Mockito.verify(recipeService, times(1)).getRecipes();
        Mockito.verify(model, times(1)).addAttribute(eq(EXPECTED_MODEL_ATTRIBUTE_NAME), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());

    }
}