package udemy.tutorials.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import udemy.tutorials.spring5recipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
