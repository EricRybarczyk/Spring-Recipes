package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;

public class RecipeControllerTest {

    public static final Long ID = 2L;
    @Mock
    private RecipeService recipeService;

    private RecipeController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetRecipe() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        // when
        Mockito.when(recipeService.findById(ArgumentMatchers.anyLong())).thenReturn(optionalRecipe);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipe() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId(ID);

        // when
        Mockito.when(recipeService.saveRecipeCommand(ArgumentMatchers.any(RecipeCommand.class))).thenReturn(command);

        // then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string value")
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testGetEditView() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId(ID);

        // when
        Mockito.when(recipeService.findCommandById(ArgumentMatchers.anyLong())).thenReturn(command);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

}