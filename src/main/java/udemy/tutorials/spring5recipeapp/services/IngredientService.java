package udemy.tutorials.spring5recipeapp.services;

import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;

public interface IngredientService {
  IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
  IngredientCommand saveIngredientCommand(IngredientCommand command);
}
