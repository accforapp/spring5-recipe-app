package udemy.tutorials.spring5recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udemy.tutorials.spring5recipeapp.commands.UnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;
import udemy.tutorials.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UomServiceImplTest {

  @Mock
  UnitOfMeasureRepository repository;

  UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

  UomService uomService;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this).close();

    unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    uomService = new UomServiceImpl(repository, unitOfMeasureToUnitOfMeasureCommand);
  }

  @Test
  void listAllUoms() {
    Set<UnitOfMeasure> returnedUom = new HashSet<>();

    UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setId(1L);

    UnitOfMeasure uom2 = new UnitOfMeasure();
    uom2.setId(2L);

    returnedUom.add(uom1);
    returnedUom.add(uom2);

    when(repository.findAll()).thenReturn(returnedUom);

    Set<UnitOfMeasureCommand> unitOfMeasureCommands = uomService.listAllUoms();

    assertNotNull(unitOfMeasureCommands);
    assertEquals(2, unitOfMeasureCommands.size());
  }

}