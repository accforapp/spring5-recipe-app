package udemy.tutorials.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.converters.RecipeCommandToRecipe;
import udemy.tutorials.spring5recipeapp.converters.RecipeToRecipeCommand;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

  private final RecipeRepository recipeRepository;
  private final RecipeCommandToRecipe recipeCommandToRecipe;
  private final RecipeToRecipeCommand recipeToRecipeCommand;

  public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                           RecipeToRecipeCommand recipeToRecipeCommand) {

    this.recipeRepository = recipeRepository;
    this.recipeCommandToRecipe = recipeCommandToRecipe;
    this.recipeToRecipeCommand = recipeToRecipeCommand;
  }

  @Override
  public Set<Recipe> getRecipes() {
    log.debug("I'm in service");
    Set<Recipe> recipes = new HashSet<>();
    recipeRepository.findAll().forEach(recipes::add);
    return recipes;
  }

  @Override
  public Recipe findById(Long id) {
    return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe Not Found!"));
  }

  @Override
  public RecipeCommand saveRecipeCommand(RecipeCommand command) {
    Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

    Recipe savedRecipe = recipeRepository.save(detachedRecipe);
    log.debug("Saved recipeId: " + savedRecipe.getId());

    return recipeToRecipeCommand.convert(savedRecipe);
  }


}
