package com.shoppingcart.dream_shops.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.model.Category;
import com.shoppingcart.dream_shops.response.ApiResponse;
import com.shoppingcart.dream_shops.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
  private final ICategoryService categoryService;

  @GetMapping("/list")
  public ResponseEntity<ApiResponse> getAllCategories() {
    try {
      List<Category> categories = categoryService.getAllCategories();
      return ResponseEntity.ok(new ApiResponse("Categories retrieved", categories));
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("Failed to retrieve categories: " + e.getMessage(), null));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("id") Long id) {
    try {
      Category category = categoryService.getCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("Category retrieved", category));
    } catch (NotFoundHttpException e) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse("Category not found: " + e.getMessage(), null));
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("Failed to retrieve category: " + e.getMessage(), null));
    }
  }

  @GetMapping("/{name}/name")
  public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("name") String name) {
    Category category = categoryService.getCategoryByName(name);
    return ResponseEntity.ok(new ApiResponse("Category retrieved", category));

  }

  @PostMapping("/create")
  public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
    Category createdCategory = categoryService.createCategory(category);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponse("Category created ", createdCategory));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Long id) {
    Category category = categoryService.getCategoryById(id);
    categoryService.deleteCategory(category.getId());
    return ResponseEntity.ok(new ApiResponse("Category deleted", null));
  }

}
