package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;
import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.services.IngredientService;
import dev.ericrybarczyk.springrecipes.services.RecipeService;
import dev.ericrybarczyk.springrecipes.services.UnitOfMeasureService;
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
import java.util.HashSet;

public class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private RecipeService recipeService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private IngredientController controller;
    private MockMvc mockMvc;
    private static final Long INGREDIENT_ID = 1L;
    private static final Long RECIPE_ID = 2L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testIngredientListView() throws Exception {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        Mockito.when(recipeService.findCommandById(ArgumentMatchers.anyLong())).thenReturn(recipeCommand);

        // when-then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(ArgumentMatchers.anyLong());
    }

    @Test
    public void testInvalidRecipeId() throws Exception {
        // given

        // when-then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/5150/ingredient"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error/error"));

        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(ArgumentMatchers.anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(ingredientCommand);

        // when-then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/2/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }

    @Test
    public void testEditIngredient() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(ingredientCommand);
        Mockito.when(unitOfMeasureService.listAllUnitsOfMeasure()).thenReturn(new HashSet<>());

        // when-then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/2/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomSet"));

    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setRecipeId(RECIPE_ID);
        Mockito.when(ingredientService.saveIngredientCommand(ArgumentMatchers.any())).thenReturn(ingredientCommand);

        // when-then
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some description")
            )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/ingredient/1/show"));
    }

}