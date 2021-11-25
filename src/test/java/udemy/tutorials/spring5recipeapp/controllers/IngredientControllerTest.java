package udemy.tutorials.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.services.IngredientService;
import udemy.tutorials.spring5recipeapp.services.RecipeService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class IngredientControllerTest {

  @Mock
  RecipeService recipeService;

  @Mock
  IngredientService ingredientService;

  IngredientController controller;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this).close();

    controller = new IngredientController(recipeService, ingredientService);

    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void testListIngredients() throws Exception {
    RecipeCommand recipeCommand = new RecipeCommand();
    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

    mockMvc.perform(get("/recipe/1/ingredients"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredients/list"))
        .andExpect(model().attributeExists("recipe"));

    verify(recipeService).findCommandById(anyLong());
  }

  @Test
  void testShowIngredient() throws Exception {
    IngredientCommand ingredientCommand = new IngredientCommand();

    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

    mockMvc.perform(get("/recipe/1/ingredient/2/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredients/show"))
        .andExpect(model().attributeExists("ingredient"));
  }
}
