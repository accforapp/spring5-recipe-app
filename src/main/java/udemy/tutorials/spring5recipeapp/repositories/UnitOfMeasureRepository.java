package udemy.tutorials.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

  Optional<UnitOfMeasure> findByDescription(String description);
}
