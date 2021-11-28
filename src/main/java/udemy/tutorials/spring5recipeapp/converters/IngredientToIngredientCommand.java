package udemy.tutorials.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

  private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

  public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
    this.uomConverter = uomConverter;
  }

  @Override
  public IngredientCommand convert(Ingredient source) {

    if (source == null) {
      return null;
    }

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(source.getId());
    ingredientCommand.setDescription(source.getDescription());
    ingredientCommand.setAmount(source.getAmount());
    ingredientCommand.setUom(uomConverter.convert(source.getUom()));

    if (source.getRecipe() != null) {
      ingredientCommand.setRecipeId(source.getRecipe().getId());
    }

    return ingredientCommand;
  }
}
