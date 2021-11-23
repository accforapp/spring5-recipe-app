package udemy.tutorials.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import udemy.tutorials.spring5recipeapp.commands.NotesCommand;
import udemy.tutorials.spring5recipeapp.converters.RecipeToRecipeCommand;
import udemy.tutorials.spring5recipeapp.domain.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

//  private final RecipeToRecipeCommand recipeConverter;

  @Override
  public NotesCommand convert(Notes source) {

    if (source == null) {
      return null;
    }

    NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(source.getId());
//    notesCommand.setRecipe(recipeConverter.convert(source.getRecipe()));
    notesCommand.setRecipeNotes(source.getRecipeNotes());

    return notesCommand;
  }
}
