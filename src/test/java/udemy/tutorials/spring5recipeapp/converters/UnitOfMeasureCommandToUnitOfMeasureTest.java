package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

  static final String DESCRIPTION = "description";
  static final Long LONG_VALUE = 1L;

  UnitOfMeasureCommandToUnitOfMeasure converter;

  @BeforeEach
  void setUp() {
    converter = new UnitOfMeasureCommandToUnitOfMeasure();
  }

  @Test
  void testNullParameter() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new UnitOfMeasureCommand()));
  }

  @Test
  void convert() {
    UnitOfMeasureCommand command = new UnitOfMeasureCommand();
    command.setId(LONG_VALUE);
    command.setDescription(DESCRIPTION);

    UnitOfMeasure uom = converter.convert(command);

    assertNotNull(uom);
    assertEquals(LONG_VALUE, uom.getId());
    assertEquals(DESCRIPTION, uom.getDescription());
  }
}