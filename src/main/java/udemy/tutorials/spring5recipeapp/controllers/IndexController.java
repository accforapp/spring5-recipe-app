package udemy.tutorials.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import udemy.tutorials.spring5recipeapp.domain.Category;
import udemy.tutorials.spring5recipeapp.domain.UnitOfMeasure;
import udemy.tutorials.spring5recipeapp.repositories.CategoryRepository;
import udemy.tutorials.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Controller
public class IndexController {

  private CategoryRepository categoryRepository;
  private UnitOfMeasureRepository unitOfMeasureRepository;

  public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
    this.categoryRepository = categoryRepository;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
  }

  @RequestMapping({"", "/", "/index"})
  public String getIndexPage() {
    Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
    Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

    System.out.println("Category ID is: " + categoryOptional.get().getId());
    System.out.println("UOM ID is: " + unitOfMeasureOptional.get().getId());

    return "index";
  }
}
