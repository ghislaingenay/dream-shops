package com.shoppingcart.dream_shops.request;

import java.math.BigDecimal;

import com.shoppingcart.dream_shops.model.Category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record AddProductRequest(
    @NotEmpty(message = "Name cannot be empty") String name,
    @NotEmpty(message = "Description cannot be empty") String description,
    @Positive(message = "Price must be positive") BigDecimal price,
    int inventory,
    Category category,
    String brand) {
  // This record class is used to encapsulate the data required to add a new
  // product.
}

// @Data
// class AddProductRequest {
// private long id;
// private String name;
// private String brand;
// private BigDecimal price;
// private int inventory; // Number of items in stock
// private String description;
// private Category category; // Category name or ID
// }