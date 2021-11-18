package udemy.tutorials.spring5recipeapp.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.client.MockMvcHttpConnector;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.services.RecipeService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {

  @Mock
  RecipeService recipeService;

  @Mock
  Model model;

  IndexController indexController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    indexController = new IndexController(recipeService);
  }

  @Test
  public void testMockMVC() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("index"));
  }

  @Test
  void getIndexPage() {

    Set<Recipe> recipes = new HashSet<>();

    recipes.add(new Recipe());

//    Recipe recipe = new Recipe();
//    recipe.setId(1L);
    recipes.add(new Recipe());

    Mockito.when(recipeService.getRecipes()).thenReturn(recipes);

    String viewName = indexController.getIndexPage(model);
    ArgumentCaptor<Set> argumentCaptor = ArgumentCaptor.forClass(Set.class);

    Assertions.assertEquals("index", viewName);

    Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
    Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());
    Assertions.assertEquals(2, argumentCaptor.getValue().size());
  }
}