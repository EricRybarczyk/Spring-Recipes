package dev.ericrybarczyk.springrecipes.controllers;

import dev.ericrybarczyk.springrecipes.commands.IngredientCommand;
import dev.ericrybarczyk.springrecipes.commands.RecipeCommand;
import dev.ericrybarczyk.springrecipes.commands.UnitOfMeasureCommand;
import dev.ericrybarczyk.springrecipes.services.IngredientService;
import dev.ericrybarczyk.springrecipes.services.RecipeService;
import dev.ericrybarczyk.springrecipes.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/recipe/{id}/ingredient")
    public String getIngredients(@PathVariable String id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        if (recipeCommand != null) {
            model.addAttribute("recipe", recipeCommand);
        }
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
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

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/edit")
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

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteRecipeIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        log.debug("Deleted Ingredient ID {} for Recipe ID {} ", ingredientId, recipeId);
        return String.format("redirect:/recipe/%s/ingredient", Long.valueOf(recipeId));
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String addNewIngredient(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        if (recipeCommand == null) {
            log.warn("No data returned for requested Recipe ID {}", recipeId);
            return ERROR_VIEW; // TODO: change to return 404
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomSet", unitOfMeasureService.listAllUnitsOfMeasure());

        return "recipe/ingredient/ingredientform";
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
