package dev.ericrybarczyk.springrecipes.repositories;

import dev.ericrybarczyk.springrecipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
