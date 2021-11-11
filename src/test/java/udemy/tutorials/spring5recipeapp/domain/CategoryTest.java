package udemy.tutorials.spring5recipeapp.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CategoryTest {

  static Category category;

  @BeforeAll
  static void setUp() {
    category = new Category();
  }

  @Test
  public void getId() {
    long idValue = 4L;

    category.setId(idValue);

    Assertions.assertEquals(idValue, category.getId());
  }

  @Test
  public void getDescription() {
  }

  @Test
  public void getRecipes() {
  }
}