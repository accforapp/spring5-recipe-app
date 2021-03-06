package udemy.tutorials.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.services.ImageService;
import udemy.tutorials.spring5recipeapp.services.RecipeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

  @Mock
  ImageService imageService;

  @Mock
  RecipeService recipeService;

  ImageController imageController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this).close();

    imageController = new ImageController(imageService, recipeService);

    mockMvc = MockMvcBuilders.standaloneSetup(imageController)
        .setControllerAdvice(new ControllerExceptionHandler())
        .build();
  }

  @Test
  void showUploadForm() throws Exception {

    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

    mockMvc.perform(get("/recipe/1/image"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
        .andExpect(view().name("recipe/imageuploadform"));

    verify(recipeService).findCommandById(1L);
  }

  @Test
  void handleImagePost() throws Exception {
    MockMultipartFile file = new MockMultipartFile("imagefile", "testing.txt",
        "text/plain", "Spring Framework Guru".getBytes());

    mockMvc.perform(multipart("/recipe/1/image").file(file))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "/recipe/1/show"));

    verify(imageService).saveImageFile(1L, file);
  }

  @Test
  void renderImageFromDB() throws Exception {

    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    byte[] testImage = "fake image text".getBytes();

    recipeCommand.setImage(testImage);

    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

    MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    byte[] content = response.getContentAsByteArray();

    assertEquals(testImage.length, content.length);
  }

  @Test
  void showUploadFormNumberFormatException() throws Exception {

    mockMvc.perform(get("/recipe/sdf/image"))
        .andExpect(status().isBadRequest())
        .andExpect(view().name("400error"));

  }
}