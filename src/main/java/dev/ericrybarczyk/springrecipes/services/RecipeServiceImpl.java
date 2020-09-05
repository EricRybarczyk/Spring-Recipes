package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.converters.RecipeCommandToRecipe;
import dev.ericrybarczyk.springrecipes.converters.RecipeToRecipeCommand;
import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.exceptions.NotFoundException;
import dev.ericrybarczyk.springrecipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("*** Sample logging in the RecipeService, thanks to Lombok ***");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundException(String.format("No Recipe found for requested ID %s", id));
        }
        return optionalRecipe;
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        Optional<Recipe> optionalRecipe = this.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundException(String.format("No Recipe found for requested ID %s", id));
        }
        return recipeToRecipeCommand.convert(optionalRecipe.orElse(null));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        if (detachedRecipe != null) {
            Recipe savedRecipe = recipeRepository.save(detachedRecipe);
            log.debug("Saved Recipe with ID {}", savedRecipe.getId());
            return recipeToRecipeCommand.convert(savedRecipe);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

}
