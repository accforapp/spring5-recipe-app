package udemy.tutorials.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.services.IngredientService;
import udemy.tutorials.spring5recipeapp.services.RecipeService;
import udemy.tutorials.spring5recipeapp.services.UomService;

import java.util.HashSet;
import java.util.Set;

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

  @Mock
  UomService uomService;

  IngredientController controller;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this).close();

    controller = new IngredientController(recipeService, ingredientService, uomService);

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

  @Test
  void testNewIngredientForm() throws Exception {
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
    when(uomService.listAllUoms()).thenReturn(new HashSet<>());

    mockMvc.perform(get("/recipe/1/ingredient/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredients/ingredientform"))
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"));

    verify(recipeService).findCommandById(anyLong());
  }

  @Test
  void testUpdateIngredient() throws Exception {
    IngredientCommand ingredientCommand = new IngredientCommand();
    Set<UnitOfMeasureCommand> uoms = new HashSet<>();

    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
    when(uomService.listAllUoms()).thenReturn(uoms);

    mockMvc.perform(get("/recipe/1/ingredient/2/update"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredients/ingredientform"))
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"));
  }

  @Test
  void testDeleteIngredient() throws Exception {
    mockMvc.perform(get("/recipe/2/ingredient/3/delete")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string")
    )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/2/ingredients"));
  }
}
