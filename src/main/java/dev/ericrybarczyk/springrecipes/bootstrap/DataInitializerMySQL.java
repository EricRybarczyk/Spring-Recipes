package dev.ericrybarczyk.springrecipes.bootstrap;

import dev.ericrybarczyk.springrecipes.domain.Category;
import dev.ericrybarczyk.springrecipes.domain.UnitOfMeasure;
import dev.ericrybarczyk.springrecipes.repositories.CategoryRepository;
import dev.ericrybarczyk.springrecipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev","prod"})
public class DataInitializerMySQL implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataInitializerMySQL(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (categoryRepository.count() == 0) {
            log.debug("Loading Categories to database");
            loadCategoriesToDatabase();
        } else {
            log.debug("Categories already in database");
        }
        if (unitOfMeasureRepository.count() == 0) {
            log.debug("Loading Units of Measure to database");
            loadUnitsOfMeasureToDatabase();
        } else {
            log.debug("Units of Measure already in database");
        }
    }

    private void loadCategoriesToDatabase() {
        Category category1 = new Category();
        category1.setDescription("American");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setDescription("Italian");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setDescription("Mexican");
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setDescription("Thai");
        categoryRepository.save(category4);
    }

    private void loadUnitsOfMeasureToDatabase() {
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setDescription("Cup");
        unitOfMeasureRepository.save(unitOfMeasure1);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setDescription("Dash");
        unitOfMeasureRepository.save(unitOfMeasure2);

        UnitOfMeasure unitOfMeasure3 = new UnitOfMeasure();
        unitOfMeasure3.setDescription("Each");
        unitOfMeasureRepository.save(unitOfMeasure3);

        UnitOfMeasure unitOfMeasure4 = new UnitOfMeasure();
        unitOfMeasure4.setDescription("Ounce");
        unitOfMeasureRepository.save(unitOfMeasure4);

        UnitOfMeasure unitOfMeasure5 = new UnitOfMeasure();
        unitOfMeasure5.setDescription("Pint");
        unitOfMeasureRepository.save(unitOfMeasure5);

        UnitOfMeasure unitOfMeasure6 = new UnitOfMeasure();
        unitOfMeasure6.setDescription("Tablespoon");
        unitOfMeasureRepository.save(unitOfMeasure6);

        UnitOfMeasure unitOfMeasure7 = new UnitOfMeasure();
        unitOfMeasure7.setDescription("Teaspoon");
        unitOfMeasureRepository.save(unitOfMeasure7);

        UnitOfMeasure unitOfMeasure8 = new UnitOfMeasure();
        unitOfMeasure8.setDescription("Pinch");
        unitOfMeasureRepository.save(unitOfMeasure8);
    }

}
