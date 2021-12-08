package udemy.tutorials.spring5recipeapp.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.converters.RecipeCommandToRecipe;
import udemy.tutorials.spring5recipeapp.converters.RecipeToRecipeCommand;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.exceptions.NotFoundException;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;

  @Mock
  RecipeRepository recipeRepository;

  @Mock
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Mock
  RecipeCommandToRecipe recipeCommandToRecipe;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
  }

  @Test
  void getRecipeByIdTest() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);

    Recipe recipeReturned = recipeService.findById(1L);

    Assertions.assertNotNull(recipeReturned, "Null recipe returned");
    Mockito.verify(recipeRepository).findById(Mockito.anyLong());
    Mockito.verify(recipeRepository, Mockito.never()).findAll();
  }

  @Test
  void getRecipeByIdNotFound() {
    when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> recipeService.findById(1L));
  }

  @Test
  void getRecipeCommandByIdTest() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);

    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

    RecipeCommand commandById = recipeService.findCommandById(1L);

    assertNotNull(commandById);
    verify(recipeRepository).findById(anyLong());
    verify(recipeToRecipeCommand).convert(any(Recipe.class));
  }

  @Test
  void getRecipes() {
    Recipe recipe = new Recipe();
    Set<Recipe> recipesData = new HashSet<>();

    recipesData.add(recipe);

    when(recipeRepository.findAll()).thenReturn(recipesData);

    Set<Recipe> recipes = recipeService.getRecipes();

    Assertions.assertEquals(recipes.size(), 1);
    Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
  }

  @Test
  void deleteRecipeByIdTest() {
    Long idForDelete = 2L;

    recipeService.deleteRecipeById(idForDelete);

    verify(recipeRepository).deleteById(anyLong());
  }
}