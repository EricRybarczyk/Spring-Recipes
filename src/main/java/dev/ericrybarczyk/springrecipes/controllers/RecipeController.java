package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        Optional<Recipe> optionalRecipe = recipeService.findById(Long.valueOf(id));
        if (optionalRecipe.isPresent()) {
            model.addAttribute("recipe", optionalRecipe.get());
            return "recipe/show";
        } else {
            log.warn("No Recipe found for requested ID {}", id);
            return "error/error";
        }
    }
}
