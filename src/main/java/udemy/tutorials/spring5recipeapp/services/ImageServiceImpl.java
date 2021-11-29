package udemy.tutorials.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import udemy.tutorials.spring5recipeapp.domain.Recipe;
import udemy.tutorials.spring5recipeapp.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

  private final RecipeRepository recipeRepository;

  public ImageServiceImpl(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void saveImageFile(Long id, MultipartFile file) {

    Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

    if (optionalRecipe.isEmpty()) {
      log.error("Can't found recipe by id: " + id);
    } else {
      Recipe recipe = optionalRecipe.get();

      try {
        recipe.setImage(file.getBytes());
      } catch (IOException e) {
        log.error("Problem with getting byte from MultipartFile");
      }

      recipeRepository.save(recipe);
    }
  }
}
