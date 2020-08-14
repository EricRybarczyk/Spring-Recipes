package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String getIndexPage(Model model) {

        log.debug("*** Sample logging in the IndexController, thanks to Lombok ***");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
