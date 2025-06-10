package com.shoppingcart.dream_shops.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor
@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates a unique identifier for each category
  private long id;
  private String name;

  @OneToMany(mappedBy = "category")
  private List<Product> products;

  public Category(String name) {
    this.name = name;
  }

  public Category orElseThrow(Object object) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
  }

}
