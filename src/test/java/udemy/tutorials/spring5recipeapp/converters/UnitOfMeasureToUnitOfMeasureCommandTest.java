package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

  static final String DESCRIPTION = "description";
  static final Long LONG_VALUE = 1L;

  UnitOfMeasureToUnitOfMeasureCommand converter;

  @BeforeEach
  void setUp() {
    converter = new UnitOfMeasureToUnitOfMeasureCommand();
  }

  @Test
  void testNullParameter() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new UnitOfMeasure()));
  }

  @Test
  void convert() {
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(LONG_VALUE);
    uom.setDescription(DESCRIPTION);

    UnitOfMeasureCommand command = converter.convert(uom);

    assertNotNull(command);
    assertEquals(LONG_VALUE, command.getId());
    assertEquals(DESCRIPTION, command.getDescription());
  }
}