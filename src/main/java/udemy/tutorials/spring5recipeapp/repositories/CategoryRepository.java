package udemy.tutorials.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import udemy.tutorials.spring5recipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
