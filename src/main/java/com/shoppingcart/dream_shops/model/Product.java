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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // Maps the relationship to the
                                                                                    // Image entity
  private List<Image> images; // List of images associated with the product
}
