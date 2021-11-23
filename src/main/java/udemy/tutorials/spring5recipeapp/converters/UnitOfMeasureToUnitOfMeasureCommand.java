package udemy.tutorials.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

  @Override
  public UnitOfMeasureCommand convert(UnitOfMeasure source) {

    if (source == null) {
      return null;
    }

    final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(source.getId());
    unitOfMeasureCommand.setDescription(source.getDescription());

    return unitOfMeasureCommand;
  }
}
