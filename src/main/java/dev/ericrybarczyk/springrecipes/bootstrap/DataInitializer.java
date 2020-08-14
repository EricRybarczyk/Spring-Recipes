package dev.ericrybarczyk.springrecipes.bootstrap;

import dev.ericrybarczyk.springrecipes.domain.*;
import dev.ericrybarczyk.springrecipes.repositories.CategoryRepository;
import dev.ericrybarczyk.springrecipes.repositories.RecipeRepository;
import dev.ericrybarczyk.springrecipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final Map<String, UnitOfMeasure> unitsOfMeasure = new HashMap<>();
    private final Map<String, Category> categories = new HashMap<>();

    public static final String UNIT_EACH = "Each";
    public static final String UNIT_CUP = "Cup";
    public static final String UNIT_OUNCE = "Ounce";
    public static final String UNIT_DASH = "Dash";
    public static final String UNIT_PINT = "Pint";
    public static final String UNIT_TABLESPOON = "Tablespoon";
    public static final String UNIT_TEASPOON = "Teaspoon";
    public static final String CATEGORY_AMERICAN = "American";
    public static final String CATEGORY_MEXICAN = "Mexican";

    public DataInitializer(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional // solves intermittent org.hibernate.LazyInitializationException
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("** Loading sample data in DataInitializer ***");
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipeList = new ArrayList<>(2);

        // get UnitOfMeasure objects
        unitsOfMeasure.put(UNIT_CUP, getUnitOfMeasure(UNIT_CUP));
        unitsOfMeasure.put(UNIT_DASH, getUnitOfMeasure(UNIT_DASH));
        unitsOfMeasure.put(UNIT_EACH, getUnitOfMeasure(UNIT_EACH));
        unitsOfMeasure.put(UNIT_OUNCE, getUnitOfMeasure(UNIT_OUNCE));
        unitsOfMeasure.put(UNIT_PINT, getUnitOfMeasure(UNIT_PINT));
        unitsOfMeasure.put(UNIT_TABLESPOON, getUnitOfMeasure(UNIT_TABLESPOON));
        unitsOfMeasure.put(UNIT_TEASPOON, getUnitOfMeasure(UNIT_TEASPOON));

        // get Category objects
        categories.put(CATEGORY_AMERICAN, getCategory(CATEGORY_AMERICAN));
        categories.put(CATEGORY_MEXICAN, getCategory(CATEGORY_MEXICAN));

        recipeList.add(getTacoRecipe());
        recipeList.add(getGuacamoleRecipe());

        return recipeList;
    }

    private UnitOfMeasure getUnitOfMeasure(final String description) {
        return unitOfMeasureRepository.findByDescription(description).orElseThrow( () -> new RuntimeException("Expected Unit of Measure was not found: " + description));
    }

    private Category getCategory(final String description) {
        return categoryRepository.findByDescription(description).orElseThrow(() -> new RuntimeException("Expected Category was not found: " + description));
    }

    private Recipe getTacoRecipe() {
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.INTERMEDIATE);
        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");
        tacosRecipe.setSource("Simply Recipes");
        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setServings(6);

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!");
        tacosRecipe.setNotes(tacoNotes);

        UnitOfMeasure tablespoon = unitsOfMeasure.get(UNIT_TABLESPOON);
        UnitOfMeasure teaspoon = unitsOfMeasure.get(UNIT_TEASPOON);
        UnitOfMeasure each = unitsOfMeasure.get(UNIT_EACH);
        UnitOfMeasure cup = unitsOfMeasure.get(UNIT_CUP);
        UnitOfMeasure pint = unitsOfMeasure.get(UNIT_PINT);

        tacosRecipe.addIngredient(new Ingredient("Ancho chili powder", new BigDecimal(2), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("Dried oregano", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Dried cumin", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Chopped", new BigDecimal(1), each));
        tacosRecipe.addIngredient(new Ingredient("Finely grated orange zest", new BigDecimal(1), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("Fresh-squeezed orange juice", new BigDecimal(3), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("Boneless chicken", new BigDecimal(4), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("Small corn tortillas", new BigDecimal(8), each));
        tacosRecipe.addIngredient(new Ingredient("Packed baby arugula", new BigDecimal(3), cup));
        tacosRecipe.addIngredient(new Ingredient("Medium ripe avocados, sliced", new BigDecimal(2), each));
        tacosRecipe.addIngredient(new Ingredient("Radishes, thinly sliced", new BigDecimal(4), each));
        tacosRecipe.addIngredient(new Ingredient("Cherry tomatoes, halved", new BigDecimal(".5"), pint));
        tacosRecipe.addIngredient(new Ingredient("Red onion, thinly sliced", new BigDecimal(".25"), each));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), each));
        tacosRecipe.addIngredient(new Ingredient("Cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cup));
        tacosRecipe.addIngredient(new Ingredient("Lime, cut into wedges", new BigDecimal(4), each));

        tacosRecipe.getCategories().add(categories.get(CATEGORY_AMERICAN));
        tacosRecipe.getCategories().add(categories.get(CATEGORY_MEXICAN));

        return tacosRecipe;
    }

    private Recipe getGuacamoleRecipe() {
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chilies. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        guacRecipe.setSource("Simply Recipes");
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setServings(4);

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.");
        guacRecipe.setNotes(guacNotes);

        UnitOfMeasure tablespoon = unitsOfMeasure.get(UNIT_TABLESPOON);
        UnitOfMeasure teaspoon = unitsOfMeasure.get(UNIT_TEASPOON);
        UnitOfMeasure each = unitsOfMeasure.get(UNIT_EACH);
        UnitOfMeasure dash = unitsOfMeasure.get(UNIT_DASH);

        guacRecipe.addIngredient(new Ingredient("Ripe avocados", new BigDecimal(2), each));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon));
        guacRecipe.addIngredient(new Ingredient("Fresh lime juice or lemon juice", new BigDecimal(2), tablespoon));
        guacRecipe.addIngredient(new Ingredient("Minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoon));
        guacRecipe.addIngredient(new Ingredient("Serrano chilies, stems and seeds removed, minced", new BigDecimal(2), each));
        guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoon));
        guacRecipe.addIngredient(new Ingredient("Freshly grated black pepper", new BigDecimal(2), dash));
        guacRecipe.addIngredient(new Ingredient("Ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), each));

        guacRecipe.getCategories().add(categories.get(CATEGORY_AMERICAN));
        guacRecipe.getCategories().add(categories.get(CATEGORY_MEXICAN));

        return guacRecipe;
    }

}
