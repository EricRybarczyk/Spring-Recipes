package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.domain.Recipe;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

}
