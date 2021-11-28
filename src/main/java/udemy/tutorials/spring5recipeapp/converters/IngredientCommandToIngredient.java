package udemy.tutorials.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;
import udemy.tutorials.spring5recipeapp.domain.Recipe;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

  private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

  public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
    this.uomConverter = uomConverter;
  }

  @Override
  public Ingredient convert(IngredientCommand source) {

    if (source == null) {
      return null;
    }

    final Ingredient ingredient = new Ingredient();
    ingredient.setId(source.getId());
    ingredient.setDescription(source.getDescription());
    ingredient.setAmount(source.getAmount());
    ingredient.setUom(uomConverter.convert(source.getUom()));

    if (source.getRecipeId() != null) {
      Recipe recipe = new Recipe();
      recipe.setId(source.getRecipeId());
      ingredient.setRecipe(recipe);
      recipe.addIngredient(ingredient);
    }

    return ingredient;
  }
}
