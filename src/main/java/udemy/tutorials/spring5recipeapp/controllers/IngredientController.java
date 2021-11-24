package udemy.tutorials.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import udemy.tutorials.spring5recipeapp.services.RecipeService;

@Slf4j
@Controller
public class IngredientController {

  private final RecipeService recipeService;

  public IngredientController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping("/recipe/{id}/ingredients")
  public String listIngredients(@PathVariable String id, Model model) {
    log.debug("Getting ingredient list for recipe ID: {}", id);

    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

    return "recipe/ingredients/list";
  }
}
