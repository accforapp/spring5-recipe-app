package udemy.tutorials.spring5recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.converters.IngredientToIngredientCommand;
import udemy.tutorials.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

  IngredientToIngredientCommand converter;

  @Mock
  RecipeRepository recipeRepository;

  IngredientService ingredientService;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this).close();

    converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    ingredientService = new IngredientServiceImpl(converter, recipeRepository);
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
}