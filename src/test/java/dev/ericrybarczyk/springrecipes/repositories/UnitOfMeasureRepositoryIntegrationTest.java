package dev.ericrybarczyk.springrecipes.repositories;

import dev.ericrybarczyk.springrecipes.domain.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIntegrationTest {

    public static final String TEASPOON = "Teaspoon";
    public static final String DASH = "Dash";

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;


    @Test
    public void findByDescriptionTeaspoon() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(TEASPOON);

        assertTrue(unitOfMeasure.isPresent());
        assertEquals(TEASPOON, unitOfMeasure.get().getDescription());
    }

    @Test
    public void findByDescriptionDash() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(DASH);

        assertTrue(unitOfMeasure.isPresent());
        assertEquals(DASH, unitOfMeasure.get().getDescription());
    }
}