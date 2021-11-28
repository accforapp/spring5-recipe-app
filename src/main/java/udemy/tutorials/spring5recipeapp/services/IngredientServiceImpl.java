package udemy.tutorials.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.converters.IngredientCommandToIngredient;
import udemy.tutorials.spring5recipeapp.converters.IngredientToIngredientCommand;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;
import udemy.tutorials.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;
  private final RecipeRepository recipeRepository;
  private final UnitOfMeasureRepository uomRepository;

  public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                               IngredientCommandToIngredient ingredientCommandToIngredient,
                               RecipeRepository recipeRepository,
                               UnitOfMeasureRepository uomRepository) {
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    this.recipeRepository = recipeRepository;
    this.uomRepository = uomRepository;
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
        .map(ingredientToIngredientCommand::convert);

    if (ingredientCommand.isEmpty()) {
      log.error("ingredient not found by ID: " + id);
    }

    return ingredientCommand.get();
  }

  @Override
  public IngredientCommand saveIngredientCommand(IngredientCommand command) {
    Optional<Recipe> optionalRecipe = recipeRepository.findById(command.getRecipeId());

    if (optionalRecipe.isEmpty()) {
      log.error("Recipe not found for id: " + command.getRecipeId());
      return null;

    } else {
      Recipe recipe = optionalRecipe.get();

      Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
          .filter(ingredient -> Objects.equals(ingredient.getId(), command.getId()))
          .findFirst();

      if (ingredientOptional.isPresent()) {

        Ingredient ingredient = ingredientOptional.get();
        ingredient.setDescription(command.getDescription());
        ingredient.setAmount(command.getAmount());
        ingredient.setUom(uomRepository.findById(command.getUom().getId()).orElseThrow(() -> new RuntimeException("UOM not found")));

      } else {

        Ingredient ingredient = ingredientCommandToIngredient.convert(command);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);
      }

      Recipe savedRecipe = recipeRepository.save(recipe);

      Optional<Ingredient> savedIngredient = savedRecipe.getIngredients().stream()
          .filter(ingredient -> Objects.equals(command.getId(), ingredient.getId()))
          .findFirst();

      if (savedIngredient.isEmpty()) {
        savedIngredient = savedRecipe.getIngredients().stream()
            .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
            .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
            .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
            .findAny();
      }

      return ingredientToIngredientCommand.convert(savedIngredient.get());
    }
  }

  @Override
  public void deleteById(Long recipeId, Long ingredientId) {
    Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

    if (optionalRecipe.isEmpty()) {
      log.error("Recipe not found for id: " + recipeId);
    } else {
      Recipe recipe = optionalRecipe.get();
      Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
          .filter(ingr -> Objects.equals(ingr.getId(), ingredientId))
          .findFirst();

      if (optionalIngredient.isEmpty()) {
        log.error("Ingredient not found by id: " + ingredientId);
      } else {
        Ingredient ingredient = optionalIngredient.get();
        ingredient.setRecipe(null);
        recipe.getIngredients().remove(ingredient);
        recipeRepository.save(recipe);
      }
    }
  }
}
