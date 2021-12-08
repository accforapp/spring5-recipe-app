package udemy.tutorials.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.exceptions.NotFoundException;
import udemy.tutorials.spring5recipeapp.services.RecipeService;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

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

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView handleNotFound(Exception e) {
    log.error("Handling not found exception: " + e.getMessage(), e);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("404error");
    modelAndView.addObject("exception", e);

    return modelAndView;
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView handleBadRequest(Exception e) {
    log.error("Handling number format exception: " + e.getMessage(), e);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("400error");
    modelAndView.addObject("exception", e);

    return modelAndView;
  }
}
