package udemy.tutorials.spring5recipeapp.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    recipeService = new RecipeServiceImpl(recipeRepository);
  }

  @Test
  void getRecipeByIdTest() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);

    Recipe recipeReturned = recipeService.findById(1L);

    Assertions.assertNotNull(recipeReturned, "Null recipe returned");
    Mockito.verify(recipeRepository).findById(Mockito.anyLong());
    Mockito.verify(recipeRepository, Mockito.never()).findAll();
  }

  @Test
  void getRecipes() {
    Recipe recipe = new Recipe();
    Set<Recipe> recipesData = new HashSet<>();

    recipesData.add(recipe);

    Mockito.when(recipeRepository.findAll()).thenReturn(recipesData);

    Set<Recipe> recipes = recipeService.getRecipes();

    Assertions.assertEquals(recipes.size(), 1);
    Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
  }
}