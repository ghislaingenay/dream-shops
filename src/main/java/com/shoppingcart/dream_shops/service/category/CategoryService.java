package com.shoppingcart.dream_shops.service.category;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.exception.CategoryNotFoundException;
import com.shoppingcart.dream_shops.exception.NotFoundException;
import com.shoppingcart.dream_shops.model.Category;
import com.shoppingcart.dream_shops.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
  private final CategoryRepository categoryRepository; // use final and RequiredArgsConstructor to inject

  @Override
  public Category getCategoryById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Category not found"));
  }

  @Override
  public Category getCategoryByName(String name) {
    return categoryRepository.findByName(name)
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public Category createCategory(Category category) {
    // Implementation here
    return null;
  }

  @Override
  public Category updateCategory(Long id, Category category) {
    // Implementation here
    return null;
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
      throw new NotFoundException("Category not found");
    });
  }

}
