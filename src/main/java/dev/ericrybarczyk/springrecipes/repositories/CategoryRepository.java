package dev.ericrybarczyk.springrecipes.repositories;

import dev.ericrybarczyk.springrecipes.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
