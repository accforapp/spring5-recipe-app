package udemy.tutorials.spring5recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ImageServiceImplTest {

  @Mock
  RecipeRepository recipeRepository;

  ImageService imageService;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this).close();

    imageService = new ImageServiceImpl(recipeRepository);
  }

  @Test
  void saveImageFile() throws IOException {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    MockMultipartFile file = new MockMultipartFile("imagefile", "testing.txt",
        "text/plain", "Spring Framework Guru".getBytes());

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

    ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);

    imageService.saveImageFile(1L, file);

    verify(recipeRepository).save(captor.capture());

    Recipe savedRecipe = captor.getValue();
    assertEquals(file.getBytes().length, savedRecipe.getImage().length);
  }
}