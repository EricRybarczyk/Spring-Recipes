package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeID, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

}
