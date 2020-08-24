package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;
import dev.ericrybarczyk.springrecipes.converters.IngredientCommandToIngredient;
import dev.ericrybarczyk.springrecipes.converters.IngredientToIngredientCommand;
import dev.ericrybarczyk.springrecipes.domain.Ingredient;
import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.repositories.RecipeRepository;
import dev.ericrybarczyk.springrecipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeID, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeID);
        if (optionalRecipe.isEmpty()) {
            log.warn("Recipe not found for ID {}", recipeID);
            return null;
        }
        Recipe recipe = optionalRecipe.get();
        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
                .filter(e -> e.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();
        if (optionalIngredientCommand.isEmpty()) {
            log.warn("Ingredient ID {} not found for Recipe ID {}", ingredientId, recipeID);
            return null;
        }
        return optionalIngredientCommand.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (optionalRecipe.isEmpty()) {
            log.error("Recipe ID {} not found from IngredientCommand ID {}", ingredientCommand.getRecipeId(), ingredientCommand.getId());
            throw new RuntimeException("Recipe not found for specified IngredientCommand");
        } else {
            Recipe recipe = optionalRecipe.get();
            Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                    .filter(e -> e.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if (optionalIngredient.isPresent()) {
                // match means update existing values
                Ingredient ingredient = optionalIngredient.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setQuantity(ingredientCommand.getQuantity());
                ingredient.setUnitOfMeasure(
                        unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).orElseThrow(() -> new RuntimeException("Unit of Measure not found"))
                );
            } else {
                // no match means save as new Ingredient on the existing Recipe
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }
            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> optionalSavedIngredient = savedRecipe.getIngredients().stream()
                    .filter(e -> e.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if (optionalSavedIngredient.isPresent()) {
                return ingredientToIngredientCommand.convert(optionalSavedIngredient.get());
            } else {
                log.error("Saved Recipe {} does not contain expected Ingredient {}", ingredientCommand.getRecipeId(), ingredientCommand.getId());
                throw new RuntimeException("Saved Recipe does not contain the Ingredient that was being saved");
            }
        }
    }

}
