package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.CategoryCommand;
import udemy.tutorials.spring5recipeapp.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

  static final Long ID_VALUE = 1L;
  static final String DESCRIPTION = "description";

  CategoryCommandToCategory converter;

  @BeforeEach
  void setUp() {
    converter = new CategoryCommandToCategory();
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new CategoryCommand()));
  }

  @Test
  void convert() {
    CategoryCommand categoryCommand = new CategoryCommand();
    categoryCommand.setId(ID_VALUE);
    categoryCommand.setDescription(DESCRIPTION);

    Category category = converter.convert(categoryCommand);

    assertNotNull(category);
    assertEquals(ID_VALUE, category.getId());
    assertEquals(DESCRIPTION, category.getDescription());
  }
}