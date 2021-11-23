package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.*;
import udemy.tutorials.spring5recipeapp.domain.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

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

  RecipeToRecipeCommand converter;
  Recipe recipe;

  @BeforeEach
  void setUp() {
    converter = new RecipeToRecipeCommand(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
        new NotesToNotesCommand(), new CategoryToCategoryCommand());

    recipe = new Recipe();
    recipe.setId(ID_VALUE);
    recipe.setDescription(DESCRIPTION);
    recipe.setPrepTime(PREP_TIME);
    recipe.setCookTime(COOK_TIME);
    recipe.setServings(SERVINGS);
    recipe.setSource(SOURCE);
    recipe.setUrl(URL);
    recipe.setDirections(DIRECTIONS);
    recipe.setDifficulty(DIFFICULTY);

    Category category1 = new Category();
    category1.setId(CAT_ID1);

    Category category2 = new Category();
    category2.setId(CAT_ID2);

    Set<Category> categories = new HashSet<>(2);
    categories.add(category1);
    categories.add(category2);

    recipe.setCategories(categories);

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(ING_ID1);

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId(ING_ID2);

    Set<Ingredient> ingredients = new HashSet<>(2);
    ingredients.add(ingredient1);
    ingredients.add(ingredient2);

    recipe.setIngredients(ingredients);

    Notes notes= new Notes();
    notes.setId(NOTES_ID);

    recipe.setNotes(notes);

  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Recipe()));
  }

  @Test
  void convert() {

    RecipeCommand recipeCommand = converter.convert(this.recipe);

    assertNotNull(recipeCommand);
    assertNotNull(recipeCommand.getCategories());
    assertEquals(2, recipeCommand.getCategories().size());
    assertNotNull(recipeCommand.getIngredients());
    assertEquals(2, recipeCommand.getIngredients().size());
    assertNotNull(recipeCommand.getNotes());
    assertEquals(NOTES_ID, recipeCommand.getNotes().getId());

    assertEquals(ID_VALUE, recipeCommand.getId());
    assertEquals(DESCRIPTION, recipeCommand.getDescription());
    assertEquals(PREP_TIME, recipeCommand.getPrepTime());
    assertEquals(COOK_TIME, recipeCommand.getCookTime());
    assertEquals(SERVINGS, recipeCommand.getServings());
    assertEquals(SOURCE, recipeCommand.getSource());
    assertEquals(URL, recipeCommand.getUrl());
    assertEquals(DIRECTIONS, recipeCommand.getDirections());
    assertEquals(DIFFICULTY, recipeCommand.getDifficulty());

  }
}