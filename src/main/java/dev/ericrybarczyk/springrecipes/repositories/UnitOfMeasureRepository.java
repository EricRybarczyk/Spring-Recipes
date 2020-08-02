package dev.ericrybarczyk.springrecipes.repositories;

import dev.ericrybarczyk.springrecipes.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
