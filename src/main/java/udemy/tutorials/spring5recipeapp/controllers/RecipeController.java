package udemy.tutorials.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {

  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping("/recipe/{id}/show")
  public String showById(@PathVariable String id, Model model) {
    model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

    return "recipe/show";
  }

  @GetMapping("/recipe/new")
  public String newRecipe(Model model) {
    model.addAttribute("recipe", new RecipeCommand());

    return "recipe/recipeform";
  }

  @GetMapping("/recipe/{id}/update")
  public String updateRecipe(@PathVariable String id, Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

    return "recipe/recipeform";
  }

  @PostMapping("recipe")
  public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
    RecipeCommand savedRecipe = recipeService.saveRecipeCommand(command);

    return "redirect:/recipe/" + savedRecipe.getId() + "/show";
  }

  @GetMapping("/recipe/{id}/delete")
  public String deleteById(@PathVariable String id) {
    log.debug("Deleting ID: {}", id);

    recipeService.deleteRecipeById(Long.valueOf(id));
    return "redirect:/";
  }
}
