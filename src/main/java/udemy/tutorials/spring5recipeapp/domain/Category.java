package udemy.tutorials.spring5recipeapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;

  @ManyToMany(mappedBy = "categories")
  private Set<Recipe> recipes;

}
