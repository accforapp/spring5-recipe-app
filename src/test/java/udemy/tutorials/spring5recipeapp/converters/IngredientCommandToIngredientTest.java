package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;
import udemy.tutorials.spring5recipeapp.domain.Recipe;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

  static final Recipe RECIPE = new Recipe();
  static final BigDecimal AMOUNT = new BigDecimal("1");
  static final String DESCRIPTION = "description";
  static final Long ID_VALUE = 1L;
  static final Long UOM_ID = 2L;

  IngredientCommandToIngredient converter;
  IngredientCommand ingredientCommand;

  @BeforeEach
  void setUp() {
    converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ID_VALUE);
    ingredientCommand.setAmount(AMOUNT);
    ingredientCommand.setDescription(DESCRIPTION);
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new IngredientCommand()));
  }

  @Test
  void convert() {

    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(UOM_ID);

    ingredientCommand.setUom(unitOfMeasureCommand);

    Ingredient ingredient = converter.convert(ingredientCommand);

    assertNotNull(ingredient);
    assertNotNull(ingredient.getUom());

    assertEquals(ID_VALUE, ingredient.getId());
    assertEquals(AMOUNT, ingredient.getAmount());
    assertEquals(DESCRIPTION, ingredient.getDescription());
    assertEquals(UOM_ID, ingredient.getUom().getId());
  }

  @Test
  void convertWithNullUOM() {
    Ingredient ingredient = converter.convert(ingredientCommand);

    assertNotNull(ingredient);
    assertNull(ingredient.getUom());

    assertEquals(ID_VALUE, ingredient.getId());
    assertEquals(AMOUNT, ingredient.getAmount());
    assertEquals(DESCRIPTION, ingredient.getDescription());
  }
}