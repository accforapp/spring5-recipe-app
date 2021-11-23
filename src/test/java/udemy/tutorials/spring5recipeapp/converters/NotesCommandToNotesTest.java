package udemy.tutorials.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.tutorials.spring5recipeapp.commands.NotesCommand;
import udemy.tutorials.spring5recipeapp.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

  final Long ID_VALUE = 1L;
  final String RECIPE_NOTES = "some notes";

  NotesCommandToNotes converter;

  @BeforeEach
  void setUp() {
    converter = new NotesCommandToNotes();
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new NotesCommand()));
  }

  @Test
  void convert() {
    NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(ID_VALUE);
    notesCommand.setRecipeNotes(RECIPE_NOTES);

    Notes notes = converter.convert(notesCommand);

    assertNotNull(notes);
    assertEquals(ID_VALUE, notes.getId());
    assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
  }
}