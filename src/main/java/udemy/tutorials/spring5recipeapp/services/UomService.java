package udemy.tutorials.spring5recipeapp.services;

import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UomService {
  Set<UnitOfMeasureCommand> listAllUoms();
}
