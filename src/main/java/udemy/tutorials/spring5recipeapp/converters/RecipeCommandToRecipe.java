package udemy.tutorials.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import udemy.tutorials.spring5recipeapp.commands.RecipeCommand;
import udemy.tutorials.spring5recipeapp.domain.Category;
import udemy.tutorials.spring5recipeapp.domain.Ingredient;
import udemy.tutorials.spring5recipeapp.domain.Recipe;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

  private final IngredientCommandToIngredient ingredientConverter;
  private final NotesCommandToNotes notesConverter;
  private final CategoryCommandToCategory categoryConverter;

  public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter,
                               CategoryCommandToCategory categoryConverter) {
    this.ingredientConverter = ingredientConverter;
    this.notesConverter = notesConverter;
    this.categoryConverter = categoryConverter;
  }

  @Override
  public Recipe convert(RecipeCommand source) {

    if (source == null) {
      return null;
    }

    Recipe recipe = new Recipe();

    recipe.setId(source.getId());
    recipe.setDescription(source.getDescription());
    recipe.setPrepTime(source.getPrepTime());
    recipe.setCookTime(source.getCookTime());
    recipe.setServings(source.getServings());
    recipe.setSource(source.getSource());
    recipe.setUrl(source.getUrl());
    recipe.setDirections(source.getDirections());

    Set<Ingredient> ingredients = new HashSet<>();
    source.getIngredients().forEach(ingredient -> ingredients.add(ingredientConverter.convert(ingredient)));

    recipe.setIngredients(ingredients);
    recipe.setImage(source.getImage());
    recipe.setDifficulty(source.getDifficulty());

    if (source.getNotes() != null) {
      recipe.setNotes(notesConverter.convert(source.getNotes()));
    }

    Set<Category> categoryCommands = source.getCategories().stream()
        .map(categoryConverter::convert)
        .collect(Collectors.toSet());

    recipe.setCategories(categoryCommands);

    return recipe;
  }
}
