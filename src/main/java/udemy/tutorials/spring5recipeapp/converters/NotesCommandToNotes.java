package udemy.tutorials.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import udemy.tutorials.spring5recipeapp.commands.NotesCommand;
import udemy.tutorials.spring5recipeapp.domain.Notes;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

//  private final RecipeCommandToRecipe recipeConverter;

  @Override
  public Notes convert(NotesCommand source) {

    if (source == null) {
      return null;
    }

    Notes notes = new Notes();
    notes.setId(source.getId());
//    notes.setRecipe(recipeConverter.convert(source.getRecipe()));
    notes.setRecipeNotes(source.getRecipeNotes());

    return notes;
  }
}
