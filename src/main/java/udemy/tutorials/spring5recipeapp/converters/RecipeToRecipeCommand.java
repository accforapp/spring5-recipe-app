package udemy.tutorials.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import udemy.tutorials.spring5recipeapp.commands.CategoryCommand;
import udemy.tutorials.spring5recipeapp.commands.IngredientCommand;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.domain.Recipe;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

  private final IngredientToIngredientCommand ingredientConverter;
  private final NotesToNotesCommand notesConverter;
  private final CategoryToCategoryCommand categoryConverter;

  public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter,
                               CategoryToCategoryCommand categoryConverter) {
    this.ingredientConverter = ingredientConverter;
    this.notesConverter = notesConverter;
    this.categoryConverter = categoryConverter;
  }

  @Override
  public RecipeCommand convert(Recipe source) {

    if (source == null) {
      return null;
    }

    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(source.getId());
    recipeCommand.setDescription(source.getDescription());
    recipeCommand.setPrepTime(source.getPrepTime());
    recipeCommand.setCookTime(source.getCookTime());
    recipeCommand.setServings(source.getServings());
    recipeCommand.setSource(source.getSource());
    recipeCommand.setUrl(source.getUrl());
    recipeCommand.setDirections(source.getDirections());

    Set<IngredientCommand> ingredientCommands = new HashSet<>();
    source.getIngredients().forEach(ingredient -> ingredientCommands.add(ingredientConverter.convert(ingredient)));

    recipeCommand.setIngredients(ingredientCommands);
    recipeCommand.setImage(source.getImage());
    recipeCommand.setDifficulty(source.getDifficulty());

    if (source.getNotes() != null) {
      recipeCommand.setNotes(notesConverter.convert(source.getNotes()));
    }

    Set<CategoryCommand> categoryCommands = source.getCategories().stream()
        .map(categoryConverter::convert)
        .collect(Collectors.toSet());

    recipeCommand.setCategories(categoryCommands);

    return recipeCommand;
  }
}
