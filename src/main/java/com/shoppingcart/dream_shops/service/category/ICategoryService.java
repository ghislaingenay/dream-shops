package com.shoppingcart.dream_shops.service.category;

import java.util.List;

import com.shoppingcart.dream_shops.model.Category;

public interface ICategoryService {
  Category getCategoryById(Long id);

  Category getCategoryByName(String name);

  List<Category> getAllCategories();

  Category createCategory(Category category);

  Category updateCategory(Long id, Category category);

  void deleteCategory(Long id);

}
