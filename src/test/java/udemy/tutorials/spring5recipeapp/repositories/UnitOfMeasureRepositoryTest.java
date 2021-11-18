package udemy.tutorials.spring5recipeapp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitOfMeasureRepositoryTest {

  @Autowired
  UnitOfMeasureRepository unitOfMeasureRepository;

  @BeforeEach
  void setUp() {
  }

  @Test
  void findByDescription() {

    UnitOfMeasure teaspoon = new UnitOfMeasure();
    teaspoon.setDescription("Teaspoon");
    unitOfMeasureRepository.save(teaspoon);

    Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

    assertEquals("Teaspoon", uomOptional.get().getDescription());
  }
}