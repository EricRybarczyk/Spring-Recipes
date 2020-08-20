package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.domain.Recipe;
import java.util.Optional;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Optional<Recipe> findById(Long id);
}
