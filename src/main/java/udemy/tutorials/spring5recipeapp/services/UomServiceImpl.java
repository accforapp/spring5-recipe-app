package udemy.tutorials.spring5recipeapp.services;

import org.springframework.stereotype.Service;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;
import udemy.tutorials.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UomServiceImpl implements UomService {

  private final UnitOfMeasureRepository unitOfMeasureRepository;
  private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

  public UomServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                        UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
    this.unitOfMeasureRepository = unitOfMeasureRepository;
    this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
  }

  @Override
  public Set<UnitOfMeasureCommand> listAllUoms() {

    Set<UnitOfMeasureCommand> uom = new HashSet<>();

    unitOfMeasureRepository.findAll().forEach(unitOfMeasure -> uom.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)));

    return uom;
  }
}
