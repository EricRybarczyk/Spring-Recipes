package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.domain.Category;
import dev.ericrybarczyk.springrecipes.domain.UnitOfMeasure;
import dev.ericrybarczyk.springrecipes.repositories.CategoryRepository;
import dev.ericrybarczyk.springrecipes.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String getIndexPage() {

        // TODO: rem - console output example of using Spring Data JPA Query Methods
        Optional<Category> optionalCategory = categoryRepository.findByDescription("Mexican");
        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription("Tablespoon");

        System.out.println(optionalCategory.isPresent() ? optionalCategory.get().getDescription() : "Category not found");
        System.out.println(optionalUnitOfMeasure.isPresent() ? optionalUnitOfMeasure.get().getDescription() : "Unit of Measure not found");

        return "index";
    }
}
