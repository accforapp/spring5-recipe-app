package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.CategoryCommand;
import udemy.tutorials.spring5recipeapp.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

  final Long ID_VALUE = 1L;
  final String DESCRIPTION = "description";

  CategoryToCategoryCommand converter;

  @BeforeEach
  void setUp() {
    converter = new CategoryToCategoryCommand();
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Category()));
  }

  @Test
  void convert() {
    Category category = new Category();
    category.setId(ID_VALUE);
    category.setDescription(DESCRIPTION);

    CategoryCommand categoryCommand = converter.convert(category);

    assertNotNull(categoryCommand);
    assertEquals(ID_VALUE, categoryCommand.getId());
    assertEquals(DESCRIPTION, categoryCommand.getDescription());
  }
}