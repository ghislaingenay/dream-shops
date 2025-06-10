package com.shoppingcart.dream_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Category findByName(String name);

  // You can add more custom query methods if needed
}
