package com.shoppingcart.dream_shops.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.http_exception.AlreadyExistsHttpException;
import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
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
        .orElseThrow(() -> new NotFoundHttpException("Category not found"));
  }

  @Override
  public Category getCategoryByName(String name) {
    return categoryRepository.findByName(name);
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public Category createCategory(Category category) {
    // .filter(c -> !categoryRepository.existsByName(c.getName())) keeps the
    // category only if the condition is true.

    // If existsByName(c.getName()) returns false, the category passes the filter.

    // If a category with the same name exists (true), the filter removes it, and
    // the result is Optional.empty()
    return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
        .map(categoryRepository::save)
        .orElseThrow(
            () -> new AlreadyExistsHttpException(category.getName() + " already exists"));
  }

  @Override
  public Category updateCategory(Long id, Category category) {
    return Optional.ofNullable(getCategoryById(id)).map(existingCategory -> {
      existingCategory.setName(category.getName());
      return categoryRepository.save(existingCategory);
    })
        .orElseThrow(() -> new NotFoundHttpException("Category not found"));
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
      throw new NotFoundHttpException("Category not found");
    });
  }

}
