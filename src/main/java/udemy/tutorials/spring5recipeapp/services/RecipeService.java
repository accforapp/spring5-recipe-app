package udemy.tutorials.spring5recipeapp.services;

import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

  Set<Recipe> getRecipes();
  Recipe findById(Long id);
  RecipeCommand saveRecipeCommand(RecipeCommand command);
}
