package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.domain.Recipe;
import dev.ericrybarczyk.springrecipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    private static final String ERROR_VIEW ="error/error";

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        Optional<Recipe> optionalRecipe = recipeService.findById(Long.valueOf(id));
        if (optionalRecipe.isPresent()) {
            model.addAttribute("recipe", optionalRecipe.get());
            return "recipe/show";
        } else {
            log.warn("No Recipe found for requested ID {}", id);
            return ERROR_VIEW;
        }
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/edit")
    public String editRecipe(@PathVariable String id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        if (recipeCommand != null) {
            model.addAttribute("recipe", recipeCommand);
            return "recipe/recipeform";
        } else {
            log.warn("No Recipe found for requested ID {}", id);
            return ERROR_VIEW;
        }
    }

    @PostMapping
    @RequestMapping("recipe") // , method = RequestMethod.POST
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return String.format("redirect:/recipe/%s/show", savedCommand.getId());
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting Recipe with ID {}", id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

}
