package com.shoppingcart.dream_shops.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor => remove because we want to use a custom constructor =>
// Use @AllArgsConstructor only if you really need a constructor with all fields
// (which is rare for JPA entities).
@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY) // Automatically generates a unique identifier
                                                                          // for each product
  private long id;
  private String name;
  private String brand;
  private BigDecimal price;
  private int inventory; // Number of items in stock
  private String description;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id")
  private Category category; // Category name or ID

  public Product(String name, String brand, BigDecimal price, int inventory, String description,
      Category category) {
    this.name = name;
    this.brand = brand;
    this.price = price;
    this.inventory = inventory;
    this.description = description;
    this.category = category;
  }

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // Maps the relationship to the
                                                                                    // Image entity
  private List<Image> images; // List of images associated with the product
}
