package udemy.tutorials.spring5recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.converters.IngredientCommandToIngredient;
import udemy.tutorials.spring5recipeapp.converters.IngredientToIngredientCommand;
import udemy.tutorials.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import udemy.tutorials.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;
import udemy.tutorials.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

  IngredientToIngredientCommand ingredientToIngredientCommand;
  IngredientCommandToIngredient ingredientCommandToIngredient;

  @Mock
  RecipeRepository recipeRepository;

  @Mock
  UnitOfMeasureRepository unitOfMeasureRepository;

  IngredientService ingredientService;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this).close();

    ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
        recipeRepository, unitOfMeasureRepository);
  }

  @Test
  void findByRecipeIdAndIngredientId() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    Ingredient ingredient = new Ingredient();
    ingredient.setId(2L);

    recipe.getIngredients().add(ingredient);

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

    IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 2L);

    assertNotNull(ingredientCommand);
    assertEquals(2L, ingredientCommand.getId());

    verify(recipeRepository).findById(anyLong());
  }

  @Test
  void saveIngredientCommand() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    Ingredient ingredient = new Ingredient();
    ingredient.setId(2L);

    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(3L);

    ingredient.setUom(unitOfMeasure);

    recipe.addIngredient(ingredient);

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));
    when(recipeRepository.save(any())).thenReturn(recipe);

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(2L);
    ingredientCommand.setRecipeId(1L);

    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(3L);

    ingredientCommand.setUom(unitOfMeasureCommand);

    IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

    assertNotNull(savedIngredientCommand);
    assertEquals(2L, savedIngredientCommand.getId());
    assertEquals(1L, savedIngredientCommand.getRecipeId());
  }

  @Test
  void deleteByIdTest() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(10L);

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId(20L);

    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    when(recipeRepository.save(any())).thenReturn(recipe);

    ingredientService.deleteById(1L, 10L);

    verify(recipeRepository).findById(1L);
    verify(recipeRepository).save(any(Recipe.class));
  }
}