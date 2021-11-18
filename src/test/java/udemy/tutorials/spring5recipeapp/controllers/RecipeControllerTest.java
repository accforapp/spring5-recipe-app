package udemy.tutorials.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.services.RecipeService;

public class RecipeControllerTest {

  @Mock
  RecipeService recipeService;

  RecipeController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    controller = new RecipeController(recipeService);
  }

  @Test
  void testGetRecipe() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
  }
}
