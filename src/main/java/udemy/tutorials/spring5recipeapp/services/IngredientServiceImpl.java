package udemy.tutorials.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.converters.IngredientToIngredientCommand;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

  private final IngredientToIngredientCommand converter;
  private final RecipeRepository recipeRepository;

  public IngredientServiceImpl(IngredientToIngredientCommand converter, RecipeRepository recipeRepository) {
    this.converter = converter;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

    if (recipeOptional.isEmpty()) {
      log.error("recipe not found by ID: " + recipeId);
    }

    Recipe recipe = recipeOptional.get();

    Optional<IngredientCommand> ingredientCommand = recipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(id))
        .findFirst()
        .map(converter::convert);

    if (ingredientCommand.isEmpty()) {
      log.error("ingredient not found by ID: " + id);
    }

    return ingredientCommand.get();
  }
}
