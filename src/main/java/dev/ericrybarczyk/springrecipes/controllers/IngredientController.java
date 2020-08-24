package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;
import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.services.IngredientService;
import dev.ericrybarczyk.springrecipes.services.RecipeService;
import dev.ericrybarczyk.springrecipes.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static dev.ericrybarczyk.springrecipes.controllers.ViewConstants.ERROR_VIEW;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredient")
    public String getIngredients(@PathVariable String id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        if (recipeCommand != null) {
            model.addAttribute("recipe", recipeCommand);
            return "recipe/ingredient/list";
        } else {
            log.warn("No Recipe found for requested ID {}", id);
            return ERROR_VIEW; // TODO: change to return 404
        }
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        if (ingredientCommand != null) {
            model.addAttribute("ingredient", ingredientCommand);
            return "recipe/ingredient/show";
        } else {
            log.warn("No data returned for requested Recipe ID {} and Ingredient ID {}", recipeId, ingredientId);
            return ERROR_VIEW; // TODO: change to return 404
        }
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/edit")
    public String editRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        if (ingredientCommand != null) {
            model.addAttribute("ingredient", ingredientCommand);
            model.addAttribute("uomSet", unitOfMeasureService.listAllUnitsOfMeasure());
            return "recipe/ingredient/ingredientform";
        } else {
            log.warn("No data returned for requested Recipe ID {} and Ingredient ID {}", recipeId, ingredientId);
            return ERROR_VIEW; // TODO: change to return 404
        }
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedIngredientCommand;
        try {
            savedIngredientCommand = ingredientService.saveIngredientCommand(command);
        } catch (NullPointerException npe) {
            log.error("NullPointerException from ingredientService.saveIngredientCommand()");
            return ERROR_VIEW; // TODO: change to return 404
        }
        if (savedIngredientCommand == null) {
            log.error("Null result attempting to save Ingredient ID {} with Recipe ID {}", command.getId(), command.getRecipeId());
            return ERROR_VIEW; // TODO: change to return 404
        }
        log.debug("Saved Ingredient ID {} with Recipe ID {}", savedIngredientCommand.getId(), savedIngredientCommand.getRecipeId());
        return String.format("redirect:/recipe/%s/ingredient/%s/show", savedIngredientCommand.getRecipeId(), savedIngredientCommand.getId());
    }

}
