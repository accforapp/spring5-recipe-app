package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.*;
import udemy.tutorials.spring5recipeapp.domain.Difficulty;
import udemy.tutorials.spring5recipeapp.domain.Recipe;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

  final Long ID_VALUE = 1L;
  final String DESCRIPTION = "description";
  final Integer PREP_TIME = 20;
  final Integer COOK_TIME = 30;
  final Integer SERVINGS = 4;
  final String SOURCE = "source";
  final String URL = "test url";
  final String DIRECTIONS = "directions";
  final Difficulty DIFFICULTY = Difficulty.EASY;
  final Long CAT_ID1 = 2L;
  final Long CAT_ID2 = 3L;
  final Long ING_ID1 = 20L;
  final Long ING_ID2 = 30L;
  final Long NOTES_ID = 9L;

  RecipeCommandToRecipe converter;
  RecipeCommand recipeCommand;

  @BeforeEach
  void setUp() {
    converter = new RecipeCommandToRecipe(new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
        new NotesCommandToNotes(), new CategoryCommandToCategory());

    recipeCommand = new RecipeCommand();
    recipeCommand.setId(ID_VALUE);
    recipeCommand.setDescription(DESCRIPTION);
    recipeCommand.setPrepTime(PREP_TIME);
    recipeCommand.setCookTime(COOK_TIME);
    recipeCommand.setServings(SERVINGS);
    recipeCommand.setSource(SOURCE);
    recipeCommand.setUrl(URL);
    recipeCommand.setDirections(DIRECTIONS);
    recipeCommand.setDifficulty(DIFFICULTY);

    CategoryCommand categoryCommand1 = new CategoryCommand();
    categoryCommand1.setId(CAT_ID1);

    CategoryCommand categoryCommand2 = new CategoryCommand();
    categoryCommand2.setId(CAT_ID2);

    Set<CategoryCommand> categoryCommands = new HashSet<>(2);
    categoryCommands.add(categoryCommand1);
    categoryCommands.add(categoryCommand2);

    recipeCommand.setCategories(categoryCommands);

    IngredientCommand ingredientCommand1 = new IngredientCommand();
    ingredientCommand1.setId(ING_ID1);

    IngredientCommand ingredientCommand2 = new IngredientCommand();
    ingredientCommand2.setId(ING_ID2);

    Set<IngredientCommand> ingredientCommands = new HashSet<>(2);
    ingredientCommands.add(ingredientCommand1);
    ingredientCommands.add(ingredientCommand2);

    recipeCommand.setIngredients(ingredientCommands);

    NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(NOTES_ID);

    recipeCommand.setNotes(notesCommand);

  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new RecipeCommand()));
  }

  @Test
  void convert() {

    Recipe recipe = converter.convert(recipeCommand);

    assertNotNull(recipe);
    assertNotNull(recipe.getCategories());
    assertEquals(2, recipe.getCategories().size());
    assertNotNull(recipe.getIngredients());
    assertEquals(2, recipe.getIngredients().size());
    assertNotNull(recipe.getNotes());
    assertEquals(NOTES_ID, recipe.getNotes().getId());

    assertEquals(ID_VALUE, recipe.getId());
    assertEquals(DESCRIPTION, recipe.getDescription());
    assertEquals(PREP_TIME, recipe.getPrepTime());
    assertEquals(COOK_TIME, recipe.getCookTime());
    assertEquals(SERVINGS, recipe.getServings());
    assertEquals(SOURCE, recipe.getSource());
    assertEquals(URL, recipe.getUrl());
    assertEquals(DIRECTIONS, recipe.getDirections());
    assertEquals(DIFFICULTY, recipe.getDifficulty());

  }
}