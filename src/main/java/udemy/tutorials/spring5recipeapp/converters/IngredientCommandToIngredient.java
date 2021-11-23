package udemy.tutorials.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

//  private final RecipeCommandToRecipe recipeConverter;
  private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

  public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
//    this.recipeConverter = recipeConverter;
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
//    ingredient.setRecipe(recipeConverter.convert(source.getRecipe()));
    ingredient.setUom(uomConverter.convert(source.getUom()));

    return ingredient;
  }
}
