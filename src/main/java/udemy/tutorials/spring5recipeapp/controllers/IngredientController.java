package udemy.tutorials.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.services.IngredientService;
import udemy.tutorials.spring5recipeapp.services.RecipeService;
import udemy.tutorials.spring5recipeapp.services.UomService;

@Slf4j
@Controller
public class IngredientController {

  private final RecipeService recipeService;
  private final IngredientService ingredientService;
  private final UomService uomService;

  public IngredientController(RecipeService recipeService, IngredientService ingredientService, UomService uomService) {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.uomService = uomService;
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

  @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
  public String updateRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id,
                                       Model model) {

    model.addAttribute("ingredient",
        ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
    model.addAttribute("uomList", uomService.listAllUoms());

    return "recipe/ingredients/ingredientform";
  }

  @GetMapping("/recipe/{recipeId}/ingredient/new")
  public String newIngredient(@PathVariable String recipeId, Model model) {

    RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(Long.valueOf(recipeId));
    ingredientCommand.setUom(new UnitOfMeasureCommand());

    model.addAttribute("ingredient", ingredientCommand);
    model.addAttribute("uomList", uomService.listAllUoms());

    return "recipe/ingredients/ingredientform";
  }

  @PostMapping("recipe/{recipeId}/ingredient")
  public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
    IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);

    log.debug("Saved recipe Id: " + ingredientCommand.getRecipeId());
    log.debug("Saved ingredient Id: " + ingredientCommand.getId());

    return "redirect:/recipe/" + ingredientCommand.getRecipeId() + "/ingredient/" + ingredientCommand.getId() + "/show";
  }

  @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
  public String deleteIngredient(@PathVariable String recipeId,
                                 @PathVariable String id) {

    log.debug("delete ingredient id: " + id);

    ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

    return "redirect:/recipe/" + recipeId + "/ingredients";
  }
}
