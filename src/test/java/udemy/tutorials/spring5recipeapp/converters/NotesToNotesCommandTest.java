package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.NotesCommand;
import udemy.tutorials.spring5recipeapp.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

  final Long ID_VALUE = 1L;
  final String RECIPE_NOTES = "some notes";

  NotesToNotesCommand converter;

  @BeforeEach
  void setUp() {
    converter = new NotesToNotesCommand();
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Notes()));
  }

  @Test
  void convert() {
    Notes notes = new Notes();
    notes.setId(ID_VALUE);
    notes.setRecipeNotes(RECIPE_NOTES);

    NotesCommand notesCommand = converter.convert(notes);

    assertNotNull(notesCommand);
    assertEquals(ID_VALUE, notesCommand.getId());
    assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
  }
}