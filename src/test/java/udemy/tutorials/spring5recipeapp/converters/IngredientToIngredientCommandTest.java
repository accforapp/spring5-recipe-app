package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

  final Long ID_VALUE = 1L;
  final String DESCRIPTION = "description";
  final BigDecimal AMOUNT = new BigDecimal("1");
  final Long UOM_ID = 2L;

  IngredientToIngredientCommand converter;
  Ingredient ingredient;

  @BeforeEach
  void setUp() {
    converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

    ingredient = new Ingredient();
    ingredient.setId(ID_VALUE);
    ingredient.setDescription(DESCRIPTION);
    ingredient.setAmount(AMOUNT);
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Ingredient()));
  }

  @Test
  void convert() {

    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(UOM_ID);

    ingredient.setUom(unitOfMeasure);

    IngredientCommand ingredientCommand = converter.convert(ingredient);

    assertNotNull(ingredientCommand);
    assertNotNull(ingredientCommand.getUom());
    assertEquals(ID_VALUE, ingredientCommand.getId());
    assertEquals(DESCRIPTION, ingredientCommand.getDescription());
    assertEquals(AMOUNT, ingredientCommand.getAmount());
    assertEquals(UOM_ID, ingredientCommand.getUom().getId());
  }

  @Test
  void convertWithNullUOM() {
    IngredientCommand ingredientCommand = converter.convert(ingredient);

    assertNotNull(ingredientCommand);
    assertNull(ingredientCommand.getUom());
    assertEquals(ID_VALUE, ingredientCommand.getId());
    assertEquals(DESCRIPTION, ingredientCommand.getDescription());
    assertEquals(AMOUNT, ingredientCommand.getAmount());
  }
}