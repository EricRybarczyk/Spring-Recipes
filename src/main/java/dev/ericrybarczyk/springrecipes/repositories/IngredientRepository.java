package dev.ericrybarczyk.springrecipes.repositories;

import dev.ericrybarczyk.springrecipes.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
