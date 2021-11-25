package udemy.tutorials.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import udemy.tutorials.spring5recipeapp.services.IngredientService;
import udemy.tutorials.spring5recipeapp.services.RecipeService;

@Slf4j
@Controller
public class IngredientController {

  private final RecipeService recipeService;
  private final IngredientService ingredientService;

  public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
  }

  @GetMapping("/recipe/{id}/ingredients")
  public String listIngredients(@PathVariable String id, Model model) {
    log.debug("Getting ingredient list for recipe ID: {}", id);

    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

    return "recipe/ingredients/list";
  }

  @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
  public String showRecipeIngredient(@PathVariable String recipeId,
                                     @PathVariable String id,
                                     Model model) {

    model.addAttribute("ingredient",
        ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

    return "recipe/ingredients/show";
  }
}
