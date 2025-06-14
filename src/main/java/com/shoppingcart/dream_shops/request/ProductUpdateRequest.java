package com.shoppingcart.dream_shops.request;

import java.math.BigDecimal;

import com.shoppingcart.dream_shops.model.Category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record ProductUpdateRequest(@NotEmpty(message = "Name cannot be empty") String name,
    @NotEmpty(message = "Description cannot be empty") String description,
    @Positive(message = "Price must be positive") BigDecimal price,
    int inventory,
    Category category,
    String brand) {

}
